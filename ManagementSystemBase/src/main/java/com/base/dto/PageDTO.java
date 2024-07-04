package com.base.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO<T> {

    /**
     * 分页数据
     */
    public List<T> items;

    /**
     * 数据总条数
     */
    public Long counts;

    /**
     * 数据第几页
     */
    public Integer pageNo;

    /**
     * 数据每页条数
     */
    public Integer pageSize;
}
