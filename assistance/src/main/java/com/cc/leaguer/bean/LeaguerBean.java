/**
 * 
 */
package com.cc.leaguer.bean;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;

/**
 * 会员
 * @author Administrator
 *
 */
@Table(name="t_leaguer")
public class LeaguerBean extends BaseOrm<LeaguerBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 270033360005242863L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 会员名称
	 */
	private String leaguerName;
	
	/**
	 * 微信opernid
	 */
	private String openid;
	
	/**
	 * 会员头像
	 */
	private String avatarUrl;
	
	/**
	 * 会员所在区域
	 */
	private Long locationId;
	
	/**
	 * 会员状态
	 */
	private String status;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	private Long uid;
	
	/**
	 * 邮箱地址
	 */
	private String email;
	
	/**
	 * 信誉
	 */
	private Float credit;
	/* (non-Javadoc)
	 * @see com.cc.common.orm.BaseOrm#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}
	/**
	 * @return the leaguerName
	 */
	public String getLeaguerName() {
		return leaguerName;
	}
	/**
	 * @param leaguerName the leaguerName to set
	 */
	public void setLeaguerName(String leaguerName) {
		this.leaguerName = leaguerName;
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
	 * @return the avatarUrl
	 */
	public String getAvatarUrl() {
		return avatarUrl;
	}
	/**
	 * @param avatarUrl the avatarUrl to set
	 */
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	/**
	 * @return the locationId
	 */
	public Long getLocationId() {
		return locationId;
	}
	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the uid
	 */
	public Long getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(Long uid) {
		this.uid = uid;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the credit
	 */
	public Float getCredit() {
		return credit;
	}
	/**
	 * @param credit the credit to set
	 */
	public void setCredit(Float credit) {
		this.credit = credit;
	}

}
