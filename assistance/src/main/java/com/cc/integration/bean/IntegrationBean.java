/**
 * 
 */
package com.cc.integration.bean;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;

/**
 * 积分账户
 * @author Administrator
 *
 */
@Table(name="t_integration")
public class IntegrationBean extends BaseOrm<IntegrationBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2094548065329953628L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 会员
	 */
	private Long leaguerId;
	
	/**
	 * 会员卡号
	 */
	private String cardNo;
	
	/**
	 * 消费积分
	 */
	private Long integration;
	
	/**
	 * 等级积分
	 */
	private Long gradeIntegration;
	
	/**
	 * 积分账户创建时间
	 */
	private Date createTime;
	
	/* (non-Javadoc)
	 * @see com.cc.common.orm.BaseOrm#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the integration
	 */
	public Long getIntegration() {
		return integration;
	}

	/**
	 * @param integration the integration to set
	 */
	public void setIntegration(Long integration) {
		this.integration = integration;
	}

	/**
	 * @return the gradeIntegration
	 */
	public Long getGradeIntegration() {
		return gradeIntegration;
	}

	/**
	 * @param gradeIntegration the gradeIntegration to set
	 */
	public void setGradeIntegration(Long gradeIntegration) {
		this.gradeIntegration = gradeIntegration;
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
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the leaguerId
	 */
	public Long getLeaguerId() {
		return leaguerId;
	}

	/**
	 * @param leaguerId the leaguerId to set
	 */
	public void setLeaguerId(Long leaguerId) {
		this.leaguerId = leaguerId;
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

}
