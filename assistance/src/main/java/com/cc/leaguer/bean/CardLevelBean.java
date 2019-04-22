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
@Table(name="t_card_level")
public class CardLevelBean extends BaseOrm<CardLevelBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3329629441453305043L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 会员卡
	 */
	private Long cardId;
	
	/**
	 * 会员级别编码
	 */
	private String level;
	
	/**
	 * 会员级别名称
	 */
	private String name;
	
	/**
	 * 会员级别卡图片
	 */
	private String imageUrl;
	
	/**
	 * 会员级别最低积分
	 */
	private Long gradeIntegration;
	
	/**
	 * 字体颜色
	 */
	private String color;

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
	 * @return the cardId
	 */
	public Long getCardId() {
		return cardId;
	}

	/**
	 * @param cardId the cardId to set
	 */
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
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
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
}
