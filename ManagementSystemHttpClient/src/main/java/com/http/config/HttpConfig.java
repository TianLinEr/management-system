package com.http.config;

import com.http.client.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration(proxyBeanMethods = false)
public class HttpConfig {
    @Bean
    public ProjectHttp requestService1() {
        WebClient webClient = WebClient.builder().build();
        return HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build()
                .createClient(ProjectHttp.class);
    }

    @Bean
    public CommentHttp requestService2() {
        WebClient webClient = WebClient.builder().build();
        return HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build()
                .createClient(CommentHttp.class);
    }

    @Bean
    public DocumentHttp requestService3() {
        WebClient webClient = WebClient.builder().build();
        return HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build()
                .createClient(DocumentHttp.class);
    }

    @Bean
    public TaskHttp requestService4() {
        WebClient webClient = WebClient.builder().build();
        return HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build()
                .createClient(TaskHttp.class);
    }

    @Bean
    public UserHttp requestService5() {
        WebClient webClient = WebClient.builder().build();
        return HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build()
                .createClient(UserHttp.class);
    }

    @Bean
    public TeamHttp requestService6() {
        WebClient webClient = WebClient.builder().build();
        return HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build()
                .createClient(TeamHttp.class);
    }
}
