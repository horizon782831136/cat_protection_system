package com.liuwei.admin.utils;

import cn.hutool.core.util.ObjectUtil;
import com.liuwei.framework.domain.Data;
import com.liuwei.framework.enums.Default;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MediaUtils<T extends Data> {

    public boolean dataValidate(T t){
        boolean flag = false;
        if (ObjectUtil.isEmpty(t.getUpdateTime())) {
            //判断是否过期
            if (t.getCreateTime().getTime() + TimeUnit.DAYS.toMillis(Integer.valueOf(Default.DURATION.getKey())) < new Date().getTime()) {
                flag = true;
            }
        } else {
            if (t.getUpdateTime().getTime() + TimeUnit.DAYS.toMillis(Integer.valueOf(Default.DURATION.getKey())) < new Date().getTime()) {
                flag = true;
            }
        }
        return flag;
    }

    public T updateDataPath(T t) throws Exception{
        String path = MinioUtils.getPresignedUrl(Default.BUCKET_NAME.getKey(), t.getName(), Integer.valueOf(Default.DURATION.getKey()),
                TimeUnit.DAYS);
        t.setPath(path);
        t.setUpdateTime(new Date());
        return t;
    }

//    public static boolean imageValidate(Image image) {
//        //未刷新
//        boolean flag = false;
//        if (ObjectUtil.isEmpty(image.getUpdateTime())) {
//            //判断是否过期
//            if (image.getCreateTime().getTime() + TimeUnit.DAYS.toMillis(Integer.valueOf(Default.DURATION.getKey())) < new Date().getTime()) {
//                flag = true;
//            }
//        } else {
//            if (image.getUpdateTime().getTime() + TimeUnit.DAYS.toMillis(Integer.valueOf(Default.DURATION.getKey())) < new Date().getTime()) {
//                flag = true;
//            }
//        }
//        return flag;
//    }
//
//    public static boolean mediaValidate(Media media) {
//        //未刷新
//        boolean flag = false;
//        if (ObjectUtil.isEmpty(media.getUpdateTime())) {
//            //判断是否过期
//            if (media.getCreateTime().getTime() + TimeUnit.DAYS.toMillis(Integer.valueOf(Default.DURATION.getKey())) < new Date().getTime()) {
//                flag = true;
//            }
//        } else {
//            if (media.getUpdateTime().getTime() + TimeUnit.DAYS.toMillis(Integer.valueOf(Default.DURATION.getKey())) < new Date().getTime()) {
//                flag = true;
//            }
//        }
//        return flag;
//    }
//
//    public static Image updateImagePath(Image image) throws Exception {
//        //未刷新
//        String path = MinioUtils.getPresignedUrl(Default.BUCKET_NAME.getKey(), image.getName(), Integer.valueOf(Default.DURATION.getKey()),
//                TimeUnit.DAYS);
//        image.setPath(path);
//                image.alter();
//        return image;
//    }
//
//    public static Media updateMediaPath(Media media) throws Exception {
//        //未刷新
//        String path = MinioUtils.getPresignedUrl(Default.BUCKET_NAME.getKey(), media.getName(), Integer.valueOf(Default.DURATION.getKey()), TimeUnit.DAYS);
//        media.setPath(path).alter();
//        return media;
//    }
}
