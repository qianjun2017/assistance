/**
 * 
 */
package com.cc.film.http.response.data;

/**
 * 演员数据
 * @author Administrator
 *
 */
public class FilmActorData {

	/**
	 * 演员名称
	 */
	private String actor;
	
	/**
	 * 演员简介
	 */
	private String desc;

	/**
	 * @return the actor
	 */
	public String getActor() {
		return actor;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param actor the actor to set
	 */
	public void setActor(String actor) {
		this.actor = actor;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
