package com.team.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                // 接口文档标题
                .info(new Info().title("团队基础信息")
                        // 接口文档简介
                        .description("电力项目协作系统-团队基础信息")
                        // 接口文档版本
                        .version("v1.0")
                        // 开发者联系方式
                        .contact(new Contact().name("psl").email("2370453803@qq.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringBoot3.2.4")
                        .url("http://localhost:8091"));
    }
}

