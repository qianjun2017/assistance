/**
 * 
 */
package com.cc.lottery.form;

/**
 * @author Administrator
 *
 */
public class LotteryPrizeForm {

	private Long id;
	
	/**
	 * 奖项名称
	 */
	private String name;
	
	/**
	 * 奖项总数量
	 */
	private Integer total;
	
	/**
	 * 奖项剩余数量
	 */
	private Integer quantity;
	
	/**
	 * 万次抽奖奖项中奖次数
	 */
	private Integer weight;

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
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the weight
	 */
	public Integer getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
}
