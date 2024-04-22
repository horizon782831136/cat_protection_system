package com.liuwei.framework.service;

import cn.hutool.core.lang.UUID;

import org.springframework.stereotype.Service;

@Service
public class UniqueIdGeneratorService {
    public String generateUniqueId(){
        return UUID.fastUUID().toString();
    }


    public String generateUniqueId(String name){
        int idx = name.lastIndexOf(".");
        if (idx != -1)
        {
            String suffix = name.substring(idx + 1);
            name = generateUniqueId() + "." + suffix;
        }
        return name;
    }
}
