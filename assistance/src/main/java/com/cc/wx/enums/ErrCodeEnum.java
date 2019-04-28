/**
 * 
 */
package com.cc.wx.enums;

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

	INVALIDREQUEST("INVALID_REQUEST", "参数格式有误或者未按规则上传", "订单重入时，要求参数值与原请求一致，请确认参数问题"),
	NOAUTH("NOAUTH", "商户未开通此接口权限", "请商户前往申请此接口权限"),
	NOTENOUGH("NOTENOUGH", "用户帐号余额不足", "用户帐号余额不足，请用户充值或更换支付卡后再支付"),
	ORDERPAID("ORDERPAID", "商户订单已支付，无需重复操作", "商户订单已支付，无需更多操作"),
	ORDERCLOSED("ORDERCLOSED", "当前订单已关闭，无法支付", "当前订单已关闭，请重新下单"),
	SYSTEMERROR("SYSTEMERROR", "系统超时", "系统异常，请用相同参数重新调用"),
	APPIDNOTEXIST("APPID_NOT_EXIST", "参数中缺少APPID", "请检查APPID是否正确"),
	MCHIDNOTEXIST("MCHID_NOT_EXIST", "参数中缺少MCHID", "请检查MCHID是否正确"),
	APPIDMCHIDNOTMATCH("APPID_MCHID_NOT_MATCH", "appid和mch_id不匹配", "请确认appid和mch_id是否匹配"),
	LACKPARAMS("LACK_PARAMS", "缺少必要的请求参数", "请检查参数是否齐全"),
	OUTTRADENOUSED("OUT_TRADE_NO_USED", "同一笔交易不能多次提交", "请核实商户订单号是否重复提交"),
	SIGNERROR("SIGNERROR", "参数签名结果不正确", "请检查签名参数和方法是否都符合签名算法要求"),
	XMLFORMATERROR("XML_FORMAT_ERROR", "XML格式错误", "请检查XML参数格式是否正确"),
	REQUIREPOSTMETHOD("REQUIRE_POST_METHOD", "未使用post传递参数", "请检查请求参数是否通过post方法提交"),
	POSTDATAEMPTY("POST_DATA_EMPTY", "post数据不能为空", "请检查post数据是否为空"),
	NOTUTF8("NOT_UTF8", "未使用指定编码格式", "请使用UTF-8编码格式");

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 说明
	 */
	private String name;
	
	/**
	 * 解决方案
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
	 * 获取微信支付错误状态
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
			throw new LogicException("E001", "微信支付错误错误不唯一,微信支付错误状态编码["+code+"]");
		}
		return errCodeEnumList.get(0);
	}
	
	/**
	 * 获取微信支付错误说明
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
	 * 获取微信支付错误解决方案
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
