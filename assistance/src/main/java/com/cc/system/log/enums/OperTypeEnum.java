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
 * 操作类型
 * @author Administrator
 *
 */
public enum OperTypeEnum {
	ADD("add", "新增"),
	UPDATE("update", "修改"),
	DELETE("delete", "删除"),
	AUTHORIZE("authorize", "授权"),
	LOGIN("login", "登录"),
	LOGOUT("logout", "退出"),
	LOCK("lock", "锁定"),
	UNLOCK("unlock", "解锁"),
	UP("up", "上架"),
	DOWN("down", "下架"),
	SYNC("sync", "同步"),
	PUSH("push", "推送"),
	REGISTER("register", "注册"),
	OPEN("open", "开启"),
	CLOSE("close", "关闭"),
	BIND("bind", "绑定"),
	THUMB("thumb", "点赞"),
	COMMENT("comment", "评论"),
	EXCHANGE("exchange", "兑换"),
	PARTICIPATE("participate", "参与"),
	AUDIT("audit", "审核"),
	DEPOSIT("deposit", "充值"),
	CANCEL("cancel", "取消"),
	EVALUATE("evaluate", "评分"),
	PLAY("play", "播放"),
	ON("on", "启用"),
	OFF("off", "下线"),
	ATTRIBUTE("attribute", "属性"),
	STILL("still", "剧照"),
	CHECKIN("checkin", "签到"),
	SEARCH("search", "搜索"),
	CLICK("click", "点击"),
	DOWNLOAD("download", "下载"),
	ORDER("order", "排序"),
	READ("read", "阅读"),
	DOWNLOADNOW("now", "立即下载"),
	RELEASE("release", "发布"),
	ABANDON("abandon", "放弃"),
	RELOAD("reload", "重载"),
	BATCHADD("batchAdd", "批量新增"),
	ADJUST("adjust", "调整"),
	SAVEORDER("saveOrder", "下单"),
	CANCELORDER("cancelOrder", "撤单"),
	BACKORDER("backOrder", "退货"),
	RECEIVEORDER("receiveOrder", "收货"),
	DELIVERYORDER("deliveryOrder", "发货"),
	PAYORDER("payOrder", "支付"),
	LOCATE("locate", "定位"),
	OVER("over", "结束");
	
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
	private OperTypeEnum(String code, String name) {
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
	 * 获取所有的操作类型
	 * @return
	 */
	public static Map<String, String> getOperTypeMap(){
		Map<String, String> operTypeMap = new HashMap<String,String>();
		OperTypeEnum[] operTypeEnums = OperTypeEnum.values();
		if (ArrayTools.isEmptyOrNull(operTypeEnums)) {
			return operTypeMap;
		}
		for (OperTypeEnum operTypeEnum : operTypeEnums) {
			operTypeMap.put(operTypeEnum.getCode(), operTypeEnum.getName());
		}
		return operTypeMap;
	}
	
	/**
	 * 获取操作类型
	 * @param code
	 * @return
	 */
	public static OperTypeEnum getOperTypeEnumByCode(String code) {
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		OperTypeEnum[] operTypeEnums = OperTypeEnum.values();
		List<OperTypeEnum> operTypeEnumList = ArrayTools.toList(operTypeEnums).stream().filter(operTypeEnum->code.equals(operTypeEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(operTypeEnumList)){
			return null;
		}
		if (operTypeEnumList.size()>1) {
			throw new LogicException("E001", "操作日志类型不唯一,操作类型编码["+code+"]");
		}
		return operTypeEnumList.get(0);
	}
	
	/**
	 * 获取操作类型说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		OperTypeEnum operTypeEnum = OperTypeEnum.getOperTypeEnumByCode(code);
		if (operTypeEnum==null) {
			return null;
		}
		return operTypeEnum.getName();
	}
}
