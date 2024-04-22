package com.liuwei.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.admin.dao.ImageDao;
import com.liuwei.admin.domain.vo.ImageVO;
import com.liuwei.admin.service.ImageService;
import com.liuwei.admin.utils.MediaUtils;
import com.liuwei.admin.utils.MinioUtils;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.bo.MediaDetail;
import com.liuwei.framework.domain.dto.PageDTO;
import com.liuwei.framework.domain.po.Image;
import com.liuwei.framework.enums.Cate;
import com.liuwei.framework.enums.Code;
import com.liuwei.framework.enums.Default;
import com.liuwei.framework.service.UniqueIdGeneratorService;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.framework.utils.ResultUtils;
import io.minio.ObjectWriteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * (Image)表服务实现类
 *
 * @author makejava
 * @since 2024-01-02 14:29:44
 */
@Service
@RequiredArgsConstructor
public class ImageServiceImpl extends ServiceImpl<ImageDao,Image> implements ImageService {
    private final ImageDao imageDao;
    private final UniqueIdGeneratorService uniqueIdGeneratorService;
    @Override
    public Result getImageByCondition(Integer current, Integer size, Long imageId, Long userId, Integer status) {
        LambdaQueryWrapper<Image> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ObjectUtil.isNotEmpty(imageId), Image::getImageId, imageId)
                .like(ObjectUtil.isNotEmpty(userId), Image::getUserId, userId)
                .eq(ObjectUtil.isNotEmpty(status), Image::getStatus, status)
                .orderByDesc(Image::getCreateTime);
        Page<Image> page = new Page<>(current, size);
        imageDao.selectPage(page, queryWrapper);
        List<Image> images = page.getRecords();
        try{
            for(Image image : images){
                //todo 更新的参数较多
                MediaUtils<Image> mediaUtils = new MediaUtils<>();
                if(mediaUtils.dataValidate(image)){
                    image = mediaUtils.updateDataPath(image).alter();
                    imageDao.updateById(image);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        page.setRecords(images);
        PageDTO<ImageVO> of = PageDTO.of(page, ImageVO.class);
        return new Result(of);
    }

    @Override
    public Image getImageBasicInfo(Long imageId) {
        LambdaQueryWrapper<Image> imageQueryWrapper = new LambdaQueryWrapper<>();
        imageQueryWrapper.eq(Image::getImageId, imageId)
                .select(Image::getPath, Image::getOriginalName,Image::getName, Image::getCreateTime,
                        Image::getUpdateTime, Image::getImageId);
        Image one = imageDao.selectOne(imageQueryWrapper);
        return one;
    }

    @Override
    public Result addImage(MultipartFile file) {
        Image image = new Image();
        InputStream inputStream = null;
        try{
            //先检查该用户是否有该文件
            LambdaQueryWrapper<Image> queryWrapper = new LambdaQueryWrapper<>();
            //todo 后续换成实际用户
            queryWrapper.eq(Image::getUserId, Cate.TEST.getKey())
                    .eq(Image::getOriginalName, file.getOriginalFilename());
            if (imageDao.selectCount(queryWrapper) > 0){
                image = imageDao.selectOne(queryWrapper);
                ImageVO imageVO = BeanUtils.copyBean(image, ImageVO.class);
                return new Result(Code.ADD_SUCCESS.getKey(),imageVO,"该用户已上传该文件");
            }

            //todo 后续要对图片进行检查、看类型是否符合要求、大小是否符合要求等
            Pattern pattern = Pattern.compile("(.*)\\.(jpg|jpeg|png|gif|bmp|webp)$");
            Matcher matcher = pattern.matcher(file.getOriginalFilename());
            if (!matcher.find()){
                return ResultUtils.fail(Code.ADD_FAILED.getKey(),"文件类型不正确");
            }

            MediaDetail mediaDetail = new MediaDetail(file.getSize() / Integer.valueOf(Default.K.getKey()), file.getContentType());
            String name = uniqueIdGeneratorService.generateUniqueId(file.getOriginalFilename());
            inputStream = file.getInputStream();
            ObjectWriteResponse object = MinioUtils.upload(Default.BUCKET_NAME.getKey(), name, file.getSize(), inputStream);
            //使用JsonObject将将mediaDetail转成Json对象
            String detail = JSONObject.toJSONString(mediaDetail);
            //todo userId

            //todo path
            String path = MinioUtils.getPresignedUrl(Default.BUCKET_NAME.getKey(), name, Integer.valueOf(Default.DURATION.getKey()), TimeUnit.DAYS);
            image.init().setUserId(Cate.VISITOR.getKey());
                    image.setPath(path);
                    image.setName(name);
                    image.setOriginalName(file.getOriginalFilename());
                    image.setDetail(detail);


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
        boolean save = imageDao.insert(image) > 0;
        if(save){
            ImageVO imageVO = BeanUtils.copyBean(image, ImageVO.class);
            return new Result(Code.ADD_SUCCESS.getKey(), imageVO, "图片上传成功！");
        }
        return new Result(Code.ADD_FAILED.getKey(),"图片上传失败！");
    }
    @Transactional
    @Override
    public boolean deleteImage(Long imageId) {
        Image image = imageDao.selectById(imageId);
        boolean delete = imageDao.deleteById(imageId) > 0;
        //删除minio中的内容
        if(delete){
            try {
                MinioUtils.removeObject(Default.BUCKET_NAME.getKey(), image.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return delete;
    }

    @Override
    @Transactional
    public boolean deleteImages(List<Long> ids) {
        LambdaQueryWrapper<Image> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Image::getName)
                .in(Image::getImageId, ids);
        List<Image> images = imageDao.selectList(queryWrapper);
        boolean delete = imageDao.deleteBatchIds(ids) > 0;
        if(delete){
            images.forEach(image -> {
                try {
                    MinioUtils.removeObject(Default.BUCKET_NAME.getKey(), image.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        return delete;
    }
}
