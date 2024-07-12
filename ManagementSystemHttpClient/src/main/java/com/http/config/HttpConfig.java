package com.http.config;

import com.http.client.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpConfig {
    @Bean
    public CommentHttp commentHttp(RestClient.Builder restClientBuilder){
        RestClient restClient = restClientBuilder.build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(CommentHttp.class);
    }

    @Bean
    public DocumentHttp documentHttp(RestClient.Builder restClientBuilder){
        RestClient restClient = restClientBuilder.build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(DocumentHttp.class);
    }

    @Bean
    public ProjectHttp projectHttp(RestClient.Builder restClientBuilder){
        RestClient restClient = restClientBuilder.build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(ProjectHttp.class);
    }

    @Bean
    public TaskHttp taskHttp(RestClient.Builder restClientBuilder){
        RestClient restClient = restClientBuilder.build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(TaskHttp.class);
    }


    @Bean
    public UserHttp userHttp(RestClient.Builder restClientBuilder){
        RestClient restClient = restClientBuilder.build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(UserHttp.class);
    }

    @Bean
    public TeamHttp teamHttp(RestClient.Builder restClientBuilder){
        RestClient restClient = restClientBuilder.build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(TeamHttp.class);
    }
}
