package com.http.client;


import com.base.entity.Documents;
import com.base.utils.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("http://127.0.0.1:10010/document")
public interface DocumentHttp {

    @GetExchange("/{id}")
    Result<Documents> getById(@PathVariable String id);
}
