package com.wmeup.photoshare.api.config.bo;

import com.wmeup.photoshare.api.base.utils.DateUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zy on 2016/8/10.
 */
public class ConfigInfo implements Serializable{
    private static final long serialVersionUID = 1588758668182355812L;
    /**
     * 主键
     */
    private Long id;
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
     * 配置描述
     */
    private String description;
    /**
     *创建时间
     */
    private Date createTime;
    /**
     *修改时间
     */
    private Date updateTime;
    /**
     *开始时间
     */
    private Date beginTime;
    /**
     *结束时间
     */
    private Date endTime;
    /**
     * 错误信息
     */
    private String error;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {

        return  DateUtil.dateToString(createTime, DateUtil.DEFAULT_DATETIME_FORMAT_SEC);
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return  DateUtil.dateToString(updateTime, DateUtil.DEFAULT_DATETIME_FORMAT_SEC);
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getBeginTime() {
        return  DateUtil.dateToString(beginTime, DateUtil.DEFAULT_DATETIME_FORMAT_SEC);
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return  DateUtil.dateToString(endTime, DateUtil.DEFAULT_DATETIME_FORMAT_SEC);
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
