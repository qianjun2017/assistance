/**
 * 
 */
package com.cc.map.enums;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ArrayTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 *
 */
public enum ErrCodeEnum {

	ERROR1("1", "服务器内部错误", "该服务响应超时或系统内部错误"),
	ERROR2("2", "请求参数非法", "必要参数拼写错误或漏传（如query和tag请求中均未传入）"),
	ERROR3("3", "权限校验失败", "权限校验失败"),
	ERROR4("4", "配额校验失败", "服务当日调用次数已超限，请前往API控制台提升（请优先进行开发者认证）"),
	ERROR5("5", "ak不存在或者非法", "未传入ak参数；ak已被删除（可前往回收站恢复）；"),
	ERROR10("10", "上传内容超过8M", "Post上传数据不能超过8M"),
	ERROR101("101", "AK参数不存在", "请求消息没有携带AK参数"),
	ERROR102("102", "Mcode参数不存在，mobile类型mcode参数必需", "对于Mobile类型的应用请求需要携带mcode参数，该错误码代表服务器没有解析到mcode"),
	ERROR200("200", "APP不存在，AK有误请检查再重试", "根据请求的AK，找不到对应的APP"),
	ERROR201("201", "APP被用户自己禁用，请在控制台解禁", "APP被用户自己禁用，请在控制台解禁"),
	ERROR202("202", "APP被管理员删除", "恶意APP被管理员删除"),
	ERROR203("203", "APP类型错误", "当前API控制台支持Server(类型1), Mobile(类型2, 新版控制台区分为Mobile_Android(类型21)及Mobile_IPhone（类型22）及Browser（类型3），除此之外的其他类型被认为是APP类型错误"),
	ERROR210("210", "APP IP校验失败", "在申请Server类型应用的时候选择IP校验，需要填写IP白名单，如果当前请求的IP地址不在IP白名单或者不是0.0.0.0/0就认为IP校验失败"),
	ERROR211("211", "APP SN校验失败", "SERVER类型APP有两种校验方式：IP校验和SN校验，当用户请求的SN和服务端计算出来的SN不相等的时候，提示SN校验失败"),
	ERROR220("220", "APP Referer校验失败", "浏览器类型的APP会校验referer字段是否存在，且在referer白名单里面，否则返回该错误码"),
	ERROR230("230", "APP Mcode码校验失败", "服务器能解析到mcode，但和数据库中不一致，请携带正确的mcode"),
	ERROR240("240", "APP 服务被禁用", "用户在API控制台中创建或设置某APP的时候禁用了某项服务"),
	ERROR250("250", "用户不存在", "根据请求的user_id, 数据库中找不到该用户的信息，请携带正确的user_id"),
	ERROR251("251", "用户被自己删除", "该用户处于未激活状态"),
	ERROR252("252", "用户被管理员删除", "恶意用户被加入黑名单"),
	ERROR260("260", "服务不存在", "服务器解析不到用户请求的服务名称"),
	ERROR261("261", "服务被禁用", "该服务已下线"),
	ERROR301("301", "永久配额超限，限制访问", "配额超限，如果想增加配额请联系我们"),
	ERROR302("302", "天配额超限，限制访问", "配额超限，如果想增加配额请联系我们"),
	ERROR401("401", "当前并发量已经超过约定并发配额，限制访问", "并发控制超限，请控制并发量请联系我们"),
	ERROR402("402", "当前并发量已经超过约定并发配额，并且服务总并发量也已经超过设定的总并发配额，限制访问", "并发控制超限，请控制并发量请联系我们");

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 说明
	 */
	private String name;
	
	/**
	 * 注释
	 */
	private String solution;

	/**
	 * @param code
	 * @param name
	 * @param solution
	 */
	private ErrCodeEnum(String code, String name, String solution) {
		this.code = code;
		this.name = name;
		this.solution = solution;
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
	 * @return the solution
	 */
	public String getSolution() {
		return solution;
	}

	/**
	 * @param solution the solution to set
	 */
	public void setSolution(String solution) {
		this.solution = solution;
	}

	/**
	 * 获取百度地图错误状态
	 * @param code
	 * @return
	 */
	public static ErrCodeEnum getErrCodeEnumByCode(String code) {
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		ErrCodeEnum[] errCodeEnums = ErrCodeEnum.values();
		List<ErrCodeEnum> errCodeEnumList = ArrayTools.toList(errCodeEnums).stream().filter(errCodeEnum->code.equals(errCodeEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(errCodeEnumList)){
			return null;
		}
		if (errCodeEnumList.size()>1) {
			throw new LogicException("E001", "百度地图错误错误不唯一,百度地图错误状态编码["+code+"]");
		}
		return errCodeEnumList.get(0);
	}
	
	/**
	 * 获取百度地图错误说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		ErrCodeEnum errCodeEnum = ErrCodeEnum.getErrCodeEnumByCode(code);
		if (errCodeEnum==null) {
			return null;
		}
		return errCodeEnum.getName();
	}
	
	/**
	 * 获取百度地图错误注释
	 * @param code
	 * @return
	 */
	public static String getSolutionByCode(String code){
		ErrCodeEnum errCodeEnum = ErrCodeEnum.getErrCodeEnumByCode(code);
		if (errCodeEnum==null) {
			return null;
		}
		return errCodeEnum.getSolution();
	}
}
