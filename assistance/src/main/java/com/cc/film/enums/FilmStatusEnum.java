/**
 * 
 */
package com.cc.film.enums;

import java.util.List;
import java.util.stream.Collectors;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ArrayTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;

/**
 * @author Administrator
 *
 */
public enum FilmStatusEnum {

	DRAFT("draft", "未上架"),
	DOWN("down", "已下架"),
	ON("on","上架中");
	
	/**
	 * @param code
	 * @param name
	 */
	private FilmStatusEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	/**
	 * 编码
	 */
	private String code;
	
	/**
	 * 说明
	 */
	private String name;
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * 获取影片状态
	 * @param code
	 * @return
	 */
	public static FilmStatusEnum getFilmStatusEnumByCode(String code){
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		FilmStatusEnum[] filmStatusEnums = FilmStatusEnum.values();
		List<FilmStatusEnum> filmStatusEnumList = ArrayTools.toList(filmStatusEnums).stream().filter(filmStatusEnum->code.equals(filmStatusEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(filmStatusEnumList)){
			return null;
		}
		if (filmStatusEnumList.size()>1) {
			throw new LogicException("E001", "影片状态不唯一,影片状态编码["+code+"]");
		}
		return filmStatusEnumList.get(0);
	}
	
	/**
	 * 获取影片状态说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		FilmStatusEnum filmStatusEnum = FilmStatusEnum.getFilmStatusEnumByCode(code);
		if (filmStatusEnum==null) {
			return null;
		}
		return filmStatusEnum.getName();
	}

}
