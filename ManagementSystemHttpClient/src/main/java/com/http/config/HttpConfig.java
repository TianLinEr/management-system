package com.http.config;

import com.http.client.*;
import feign.Feign;
import feign.Request;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpConfig {
    @Bean
    public CommentHttp commentHttp(){
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .options(new Request.Options(1000, 3500))
                //.retryer(new Retryer.Default(5000, 5000, 3))
                .target(CommentHttp.class, "http://localhost:10010/comment");
    }

    @Bean
    public DocumentHttp documentHttp(){
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .options(new Request.Options(1000, 3500))
                //.retryer(new Retryer.Default(5000, 5000, 3))
                .target(DocumentHttp.class, "http://localhost:10010/document");
    }

    @Bean
    public ProjectHttp projectHttp(){
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .options(new Request.Options(1000, 3500))
                //.retryer(new Retryer.Default(5000, 5000, 3))
                .target(ProjectHttp.class, "http://localhost:10010/project");
    }

    @Bean
    public TaskHttp taskHttp(){
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .options(new Request.Options(1000, 3500))
                //.retryer(new Retryer.Default(5000, 5000, 3))
                .target(TaskHttp.class, "http://localhost:10010/task");
    }


    @Bean
    public UserHttp userHttp(){
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .options(new Request.Options(1000, 3500))
                //.retryer(new Retryer.Default(5000, 5000, 3))
                .target(UserHttp.class, "http://localhost:10010/user");
    }

    @Bean
    public TeamHttp teamHttp(){
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .options(new Request.Options(1000, 3500))
                //.retryer(new Retryer.Default(5000, 5000, 3))
                .target(TeamHttp.class, "http://localhost:10010/team");
    }
}
