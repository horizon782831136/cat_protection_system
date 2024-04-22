package com.liuwei.user.utils;

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


}
