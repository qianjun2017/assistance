/**
 * 
 */
package com.cc.wx.http.request;

import com.cc.wx.http.request.model.Color;

/**
 * @author Administrator
 *
 */
public class WXACodeRequest {

	/**
	 * 小程序调用凭证
	 */
	private String accessToken;
	
	/**
	 * 最大32个可见字符，只支持数字，大小写英文以及部分特殊字符：!#$&'()*+,/:;=?@-._~，其它字符请自行编码为合法字符（因不支持%，中文无法使用 urlencode 处理，请使用其他编码方式）
	 */
	private String scene;
	
	/**
	 * 必须是已经发布的小程序存在的页面（否则报错），例如 pages/index/index, 根路径前不要填加 /,不能携带参数（参数请放在scene字段里），如果不填写这个字段，默认跳主页面
	 */
	private String page;
	
	/**
	 * 二维码的宽度，单位 px，最小 280px，最大 1280px
	 */
	private Integer width;
	
	/**
	 * 自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调，默认 false
	 */
	private Boolean autoColor; 
	
	/**
	 * auto_color 为 false 时生效，使用 rgb 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"} 十进制表示
	 */
	private Color lineColor;
	
	/**
	 * 是否需要透明底色，为 true 时，生成透明底色的小程序
	 */
	private Boolean isHyaline;
	
	private String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit";

	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * @return the scene
	 */
	public String getScene() {
		return scene;
	}

	/**
	 * @param scene the scene to set
	 */
	public void setScene(String scene) {
		this.scene = scene;
	}

	/**
	 * @return the page
	 */
	public String getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}

	/**
	 * @return the autoColor
	 */
	public Boolean getAutoColor() {
		return autoColor;
	}

	/**
	 * @param autoColor the autoColor to set
	 */
	public void setAutoColor(Boolean autoColor) {
		this.autoColor = autoColor;
	}

	/**
	 * @return the lineColor
	 */
	public Object getLineColor() {
		return lineColor;
	}

	/**
	 * @param lineColor the lineColor to set
	 */
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	/**
	 * @return the isHyaline
	 */
	public Boolean getIsHyaline() {
		return isHyaline;
	}

	/**
	 * @param isHyaline the isHyaline to set
	 */
	public void setIsHyaline(Boolean isHyaline) {
		this.isHyaline = isHyaline;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
}
