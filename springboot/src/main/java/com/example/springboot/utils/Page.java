package com.example.springboot.utils;

import lombok.Data;

/**
 * @author ys
 * @date： 2024/9/24 16:47
 */

@Data
public class Page {

    /**
     * 条数
     */
    private Integer pageSize;

    /**
     * 当前页面
     */
    private Integer currentPage;

    /**
     * 总记录数
     */

}
