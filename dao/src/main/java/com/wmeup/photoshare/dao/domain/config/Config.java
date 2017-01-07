
package com.wmeup.photoshare.dao.domain.config;

import java.util.Date;

/**
* @ClassName: Config
* @Description:
* @author zhangyong
* @date 2016-8-2
*/
public class Config{
	
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


	public void setId(Long value) {
		this.id = value;
	}

	public Long getId() {
		return this.id;
	}

	public void setFlag(String value) {
		this.flag = value;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public void setState(Integer value) {
		this.state = value;
	}

	public Integer getState() {
		return this.state;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreateTime(Date value) {
		this.createTime = value;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setBeginTime(Date value) {
		this.beginTime = value;
	}

	public Date getBeginTime() {
		return this.beginTime;
	}

	public void setEndTime(Date value) {
		this.endTime = value;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setError(String value) {
		this.error = value;
	}

	public String getError() {
		return this.error;
	}
}

