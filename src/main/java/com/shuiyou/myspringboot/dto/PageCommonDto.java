package com.shuiyou.myspringboot.dto;

import lombok.Data;

@Data
public class PageCommonDto {
    /**
     * 当前页码
     */
    private Integer pageNum;
    /**
     * 一页几条数据
     */
    private Integer pageSize;
}
