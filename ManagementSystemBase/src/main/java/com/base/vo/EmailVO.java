package com.base.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmailVO {

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 邮件标题
     */
    private String title;

    /**
     * 邮件发送给谁
     */
    private String toEmail;
}
