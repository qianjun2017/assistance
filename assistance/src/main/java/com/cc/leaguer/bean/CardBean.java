/**
 * 
 */
package com.cc.leaguer.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;

/**
 * @author Administrator
 *
 */
@Table(name="t_card")
public class CardBean extends BaseOrm<CardBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5569403016499122002L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 会员卡名称
	 */
	private String name;
	
	/**
	 * 会员卡使用规则
	 */
	private byte[] rule;

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
	 * @return the rule
	 */
	public byte[] getRule() {
		return rule;
	}

	/**
	 * @param rule the rule to set
	 */
	public void setRule(byte[] rule) {
		this.rule = rule;
	}
}
