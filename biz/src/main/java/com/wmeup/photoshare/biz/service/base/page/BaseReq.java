package com.wmeup.photoshare.biz.service.base.page;

/**
 * Created by zy on 2016/8/4.
 */
public class BaseReq {
    /**
     * 当前页
     */
    private Integer pageNo;
    /**
     * 每页条数
     */
    private Integer pageSize;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
