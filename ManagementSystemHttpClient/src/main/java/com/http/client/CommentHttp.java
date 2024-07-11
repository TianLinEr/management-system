package com.http.client;


import com.base.annotation.NotNeedIntercept;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("http://127.0.0.1:10010/comment")
@NotNeedIntercept
public interface CommentHttp {


}
