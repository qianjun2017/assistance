/**
 * 
 */
package com.cc.leaguer.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.cc.common.web.QueryForm;

/**
 * @author Administrator
 *
 */
public class LeaguerQueryForm extends QueryForm {
	
	/**
	 * 会员名称
	 */
	private String leaguerName;
	
	/**
	 * 会员所属省份
	 */
	private String province;
	
	/**
	 * 会员所属市区
	 */
	private String city;
	
	/**
	 * 会员所属国家
	 */
	private String country;
	
	/**
	 * 会员状态
	 */
	private String status;
	
	/**
	 * 会员手机号码
	 */
	private String phone;
	
	/**
	 * 会员卡号
	 */
	private String cardNo;
	
	/**
	 * 会员卡级别
	 */
	private String cardLevel;

	/**
	 * 开始时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTimeStart;
	
	/**
	 * 结束时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTimeEnd;

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
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
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
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}

	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	/**
	 * @return the cardLevel
	 */
	public String getCardLevel() {
		return cardLevel;
	}

	/**
	 * @param cardLevel the cardLevel to set
	 */
	public void setCardLevel(String cardLevel) {
		this.cardLevel = cardLevel;
	}

	/**
	 * @return the createTimeStart
	 */
	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	/**
	 * @param createTimeStart the createTimeStart to set
	 */
	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	/**
	 * @return the createTimeEnd
	 */
	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	/**
	 * @param createTimeEnd the createTimeEnd to set
	 */
	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
}
