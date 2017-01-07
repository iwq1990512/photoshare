package com.wmeup.photoshare.api.config.bo;

import com.wmeup.photoshare.api.base.bo.BaseRequestInfo;

/**
 * Created by zy on 2016/8/10.
 */
public class QryPageConfigsReq extends BaseRequestInfo {
    private static final long serialVersionUID = 549380817682490397L;
    /**
     *配置类型
     */
    private String flag;
    /**
     *配置具体value
     */
    private String value;
    /**
     *是否生效: 1true, 0false
     */
    private Integer state;
    /**
     * 当前页
     */
    private Integer pageNo;
    /**
     * 每页条数
     */
    private Integer pageSize;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

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
