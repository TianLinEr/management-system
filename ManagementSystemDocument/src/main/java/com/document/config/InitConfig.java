package com.document.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InitConfig implements InitializingBean {

    @Autowired
    private MinIOUtil minioUtils;


    @Autowired
    private MinIOConfig minioConfig;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 项目启动创建Bucket，不存在则进行创建
        minioUtils.createBucket(minioConfig.getBucketName());
    }
}
