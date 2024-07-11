package com.http.client;


import com.base.annotation.NotNeedIntercept;
import com.base.entity.Documents;
import com.base.utils.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange("http://127.0.0.1:10010/document")
@NotNeedIntercept
public interface DocumentHttp {

    @GetExchange("/sel/{id}")
    Result<Documents> getById(@PathVariable String id);

    @DeleteExchange("/del/{documentIds}")
    Result<Documents> deleteById(@PathVariable List<Integer> documentIds);

    @DeleteExchange("/del-project/{projectIds}")
    Result deleteProjectIds(@PathVariable List<Integer> projectIds);
}
