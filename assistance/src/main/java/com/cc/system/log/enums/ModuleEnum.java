/**
 * 
 */
package com.cc.system.log.enums;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ArrayTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;

/**
 * 操作模块
 * @author Administrator
 *
 */
public enum ModuleEnum {

	USERMANAGEMENT("user", "用户管理"),
	ROLEMANAGEMENT("role", "角色管理"),
	AUTHMANAGEMENT("auth", "权限管理"),
	LOGINMANAGEMENT("shrio", "登陆管理"),
	CUSTOMERMANAGEMENT("customer", "会员管理"),
	CARDMANAGEMENT("card", "会员卡"),
	CARDLEVELMANAGEMENT("level", "会员卡级别"),
	FRANCHISERMANAGEMENT("franchiser", "经销商"),
	CONSULTANTMANAGEMENT("consultant", "汽车顾问"),
	POINTSMANAGEMENT("points", "积分管理"),
	CONFIGMANAGEMENT("config", "系统参数"),
	TEMPLATEMANAGEMENT("template", "我的模板"),
	PUSHMANAGEMENT("push", "消息推送"),
	PAGEMANAGEMENT("page", "页面"),
	INTEGRATIONMANAGEMENT("integration", "积分管理"),
	INTEGRATIONEVENTMANAGEMENT("integrationEvent", "积分事件管理"),
	LEAGUERMANAGEMENT("leaguer", "会员管理"),
	CHANNELMANAGEMENT("channel", "频道管理"),
	FILMMANAGEMENT("film", "影片管理"),
	ACTIVITYMANAGEMENT("activity", "活动管理"),
	EXCHANGEMANAGEMENT("exchange", "交易管理"),
	LOCATIONMANAGEMENT("location", "地区管理"),
	CAROUSELMANAGEMENT("carousel", "轮播图管理"),
	NOVELMANAGEMENT("novel", "小说管理"),
	MESSAGEMANAGEMENT("message", "消息管理"),
	CHANNELSUBJECTMANAGEMENT("subject", "专题管理"),
	SELLERMANAGEMENT("seller", "卖家管理"),
	SHOPMANAGEMENT("shop", "店铺管理"),
	GOODSCATEGORYMANAGEMENT("goodsCategory", "商品品类管理"),
	GOODSMANAGEMENT("goods", "商品管理"),
	STOCKMANAGEMENT("stock", "库存管理"),
	ORDERMANAGEMENT("order", "订单管理");
	
	/**
	 * 编码
	 */
	private String code;
	
	/**
	 * 说明
	 */
	private String name;

	/**
	 * @param code
	 * @param name
	 */
	private ModuleEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

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
	 * 获取所有的日志模块
	 * @return
	 */
	public static Map<String, String> getModuleMap(){
		Map<String, String> moduleMap = new HashMap<String,String>();
		ModuleEnum[] moduleEnums = ModuleEnum.values();
		if (ArrayTools.isEmptyOrNull(moduleEnums)) {
			return moduleMap;
		}
		for (ModuleEnum moduleEnum : moduleEnums) {
			moduleMap.put(moduleEnum.getCode(), moduleEnum.getName());
		}
		return moduleMap;
	}
	
	/**
	 * 获取日志模块
	 * @param code
	 * @return
	 */
	public static ModuleEnum getModuleEnumByCode(String code) {
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		ModuleEnum[] moduleEnums = ModuleEnum.values();
		List<ModuleEnum> moduleEnumList = ArrayTools.toList(moduleEnums).stream().filter(moduleEnum->code.equals(moduleEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(moduleEnumList)){
			return null;
		}
		if (moduleEnumList.size()>1) {
			throw new LogicException("E001", "操作日志模块不唯一,操作日志模块编码["+code+"]");
		}
		return moduleEnumList.get(0);
	}
	
	/**
	 * 获取操作模块说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		ModuleEnum moduleEnum = ModuleEnum.getModuleEnumByCode(code);
		if (moduleEnum==null) {
			return null;
		}
		return moduleEnum.getName();
	}
}
