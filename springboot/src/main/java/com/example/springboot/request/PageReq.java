package com.example.springboot.request;

import lombok.Data;

/**
 * @author ys
 * @date： 2024/9/24 16:47
 */

@Data
public class PageReq {

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 当前页码
     */
    private Integer currentPage;
    /**
     * 总记录数（可选）
     */
    private Integer totalRecords;
    /**
     * 偏移量，用于分页查询
     */
    private Integer offset;

    // 获取当前页码，默认第一页
    public Integer getCurrentPage() {
        return currentPage == null || currentPage <= 0 ? 1 : currentPage;
    }


    public Integer getPageSize() {
        return pageSize == null || pageSize <= 0 ? 10 : pageSize;
    }


    public Integer getOffset() {
        return (getCurrentPage() - 1) * getPageSize();
    }


    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
        this.offset = getOffset();
    }


    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        this.offset = getOffset();
    }
}