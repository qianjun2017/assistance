package com.cc.push.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;

@Table(name="t_push_user")
public class PushUserBean extends BaseOrm<PushUserBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6382965385809225733L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 推送
	 */
	private Long pushId;
	
	/**
	 * 用户
	 */
	private Long userId;
	
	/**
	 * 用户
	 */
	private String name;
	
	/**
	 * 用户
	 */
	private String openid;
	
	/**
	 * 发送结果
	 */
	private Boolean success;
	
	/**
	 * 发错误信息
	 */
	private String message;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the pushId
	 */
	public Long getPushId() {
		return pushId;
	}

	/**
	 * @param pushId the pushId to set
	 */
	public void setPushId(Long pushId) {
		this.pushId = pushId;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the openid
	 */
	public String getOpenid() {
		return openid;
	}

	/**
	 * @param openid the openid to set
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/**
	 * @return the success
	 */
	public Boolean getSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(Boolean success) {
		this.success = success;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
