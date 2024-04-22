package com.liuwei.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.bo.MediaDetail;
import com.liuwei.framework.domain.dto.PageDTO;
import com.liuwei.framework.domain.po.Media;
import com.liuwei.framework.enums.Code;
import com.liuwei.framework.enums.Default;
import com.liuwei.framework.enums.Status;
import com.liuwei.framework.service.UniqueIdGeneratorService;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.framework.utils.RedisCache;
import com.liuwei.framework.utils.ResultUtils;
import com.liuwei.user.dao.MediaDao;
import com.liuwei.user.domain.dto.LoginUser;
import com.liuwei.user.domain.vo.MediaVO;
import com.liuwei.user.service.MediaService;
import com.liuwei.user.utils.JwtUtils;
import com.liuwei.user.utils.MediaUtils;
import com.liuwei.user.utils.MinioUtils;
import io.jsonwebtoken.Claims;
import io.minio.ObjectWriteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * (Media)表服务实现类
 *
 * @author makejava
 * @since 2024-02-10 20:02:55
 */
@Service
@RequiredArgsConstructor
public class MediaServiceImpl extends ServiceImpl<MediaDao, Media> implements MediaService {
    private final MediaDao mediaDao;
    private final UniqueIdGeneratorService uniqueIdGeneratorService;
    private final RedisCache redisCache;

    @Override
    public Result addMedia(MultipartFile file, HttpServletRequest request) {
        //获取token
        String token = request.getHeader("Authorization");
        if(ObjectUtil.isEmpty(token)){
           return ResultUtils.add(false);
        }

        token = token.substring(7);
        //解析token
        String userid;
        //从session中获取用户信息
        try {
            Claims claims = JwtUtils.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
        //从redis中获取用户信息
        String redisKey = "login:" + userid;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        Media media = new Media();
        InputStream inputStream = null;
        try{
            //先检查该用户是否有该文件
            LambdaQueryWrapper<Media> queryWrapper = new LambdaQueryWrapper<>();

            queryWrapper.eq(Media::getUserId, loginUser.getUser().getUserId())
                    .eq(Media::getOriginalName, file.getOriginalFilename());
            if (mediaDao.selectCount(queryWrapper) > 0){
                media = mediaDao.selectOne(queryWrapper);
                MediaVO mediaVO = BeanUtils.copyBean(media, MediaVO.class);
                return new Result(Code.ADD_SUCCESS.getKey(), mediaVO, "该用户已上传该文件");
            }
            String fileType = "";// todo 后续要根据文件内容判断
            //todo 后续要对文件进行检查、看类型是否符合要求、大小是否符合要求等
            Pattern pattern = Pattern.compile("(.*)\\.(jpg|jpeg|png|gif|bmp|webp|mp3|wav|aac|flac|ogg|mp4|avi|mov|mkv|webm)$");
            Matcher matcher = pattern.matcher(file.getOriginalFilename());
            if (!matcher.find()){
                return ResultUtils.fail(Code.ADD_FAILED.getKey(),"文件类型不正确");
            }else{
                if(fileType.equals("mp3") || fileType.equals("wav") || fileType.equals("aac")
                        || fileType.equals("flac") || fileType.equals("ogg")){
                    fileType = "audio";
                }else if(fileType.equals("mp4") || fileType.equals("avi") || fileType.equals("mov") ||
                        fileType.equals("mkv") || fileType.equals("webm")){
                    fileType = "video";
                }else{
                    fileType = "image";
                }
            }

            MediaDetail mediaDetail = new MediaDetail(file.getSize() / Integer.parseInt(Default.K.getKey()), file.getContentType());
            String name = uniqueIdGeneratorService.generateUniqueId(file.getOriginalFilename());
            inputStream = file.getInputStream();
            ObjectWriteResponse object = MinioUtils.upload(Default.BUCKET_NAME.getKey(), name, file.getSize(), inputStream);
            //使用JsonObject将将mediaDetail转成Json对象
            String detail = JSONObject.toJSONString(mediaDetail);

            String path = MinioUtils.getPresignedUrl(Default.BUCKET_NAME.getKey(), name, Integer.valueOf(Default.DURATION.getKey()), TimeUnit.DAYS);
            media = media.init().setMediumType(fileType).setUserId(loginUser.getUser().getUserId());
            media.setPath(path);
            media.setName(name);
            media.setOriginalName(file.getOriginalFilename());
            media.setDetail(detail);


        } catch (Exception e){
            e.printStackTrace();
            //这里要关闭流避免文件被锁定
        } finally{
            if(ObjectUtil.isNotEmpty(inputStream)){
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        boolean save = mediaDao.insert(media) > 0;
        if(save){
            MediaVO mediaVO = BeanUtils.copyBean(media, MediaVO.class);
            return new Result(Code.ADD_SUCCESS.getKey(), mediaVO, "文件上传成功！");
        }
        return new Result(Code.ADD_FAILED.getKey(),"文件上传失败！");
    }

    @Override
    @Transactional
    public boolean deleteMedia(Long mediaId) {
        Media media = mediaDao.selectById(mediaId);
        boolean delete = mediaDao.deleteById(mediaId) > 0;
        if(delete){
            try {
                MinioUtils.removeObject(Default.BUCKET_NAME.getKey(), media.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return delete;
    }

    @Override
    public boolean deleteMedias(List<Long> ids) {
        LambdaQueryWrapper<Media> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Media::getName)
                .in(Media::getMediaId, ids);
        List<Media> medias = mediaDao.selectList(queryWrapper);
        boolean delete = mediaDao.deleteBatchIds(ids) > 0;
        if(delete){
            medias.forEach(media -> {
                try {
                    MinioUtils.removeObject(Default.BUCKET_NAME.getKey(), media.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        return delete;
    }

    @Override
    public Media getMediaBasicInfo(Long mediaId) {
       LambdaQueryWrapper<Media> queryWrapper = new LambdaQueryWrapper<>();
       queryWrapper.eq(Media::getMediaId, mediaId)
               .select(Media::getMediaId, Media::getName, Media::getCreateTime, Media::getUpdateTime, Media::getPath)
               .ge(Media::getStatus, Status.NORMAL_STATUS.getKey())
               .orderByDesc(Media::getCreateTime);
        Media media = mediaDao.selectOne(queryWrapper);
        MediaUtils<Media> mediaUtils = new MediaUtils<>();
        try{
            if(mediaUtils.dataValidate(media)){
                media = mediaUtils.updateDataPath(media).alter();
                mediaDao.updateById(media);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return media;
    }

    @Override
    public Result getMediaByUserId(Long userId, Integer current, Integer size) {
        LambdaQueryWrapper<Media> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Media::getUserId, userId)
                .select(Media::getMediaId, Media::getName, Media::getCreateTime, Media::getUpdateTime, Media::getPath,
                Media::getTopicId, Media::getMediumType, Media::getDetail, Media::getStatus)
                .ge(Media::getStatus, Status.NORMAL_STATUS.getKey());
        Page<Media> page = new Page<>(current, size);
        mediaDao.selectPage(page, queryWrapper);
        page.getRecords().forEach(media -> {
            try {
                MediaUtils<Media> mediaUtils = new MediaUtils<>();
                if(mediaUtils.dataValidate(media)){
                    media = mediaUtils.updateDataPath(media).alter();
                    mediaDao.updateById(media);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        return new Result(PageDTO.of(page, MediaVO.class));
    }


}
