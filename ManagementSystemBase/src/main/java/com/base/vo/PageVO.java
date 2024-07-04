package com.base.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PageVO {

    /**
     * 第几页
     */
    private Integer pageNo;

    /**
     * 每页数据条数
     */
    private Integer pageSize;
}
