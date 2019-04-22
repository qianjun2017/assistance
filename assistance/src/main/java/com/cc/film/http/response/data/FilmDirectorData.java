/**
 * 
 */
package com.cc.film.http.response.data;

/**
 * 导演数据
 * @author Administrator
 *
 */
public class FilmDirectorData {

	/**
	 * 演员名称
	 */
	private String director;
	
	/**
	 * 演员简介
	 */
	private String desc;

	/**
	 * @return the director
	 */
	public String getDirector() {
		return director;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param director the director to set
	 */
	public void setDirector(String director) {
		this.director = director;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
