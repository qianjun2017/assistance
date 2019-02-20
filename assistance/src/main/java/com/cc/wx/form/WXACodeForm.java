/**
 * 
 */
package com.cc.wx.form;

/**
 * @author Administrator
 *
 */
public class WXACodeForm {

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
	 * lineColor auto_color 为 false 时生效，使用 rgb 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"} 十进制表示
	 */
	private Integer r;
	
	/**
	 * lineColor auto_color 为 false 时生效，使用 rgb 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"} 十进制表示
	 */
	private Integer g;
	
	/**
	 * lineColor auto_color 为 false 时生效，使用 rgb 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"} 十进制表示
	 */
	private Integer b;
	
	/**
	 * 是否需要透明底色，为 true 时，生成透明底色的小程序
	 */
	private Boolean isHyaline;

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
	 * @return the r
	 */
	public Integer getR() {
		return r;
	}

	/**
	 * @param r the r to set
	 */
	public void setR(Integer r) {
		this.r = r;
	}

	/**
	 * @return the g
	 */
	public Integer getG() {
		return g;
	}

	/**
	 * @param g the g to set
	 */
	public void setG(Integer g) {
		this.g = g;
	}

	/**
	 * @return the b
	 */
	public Integer getB() {
		return b;
	}

	/**
	 * @param b the b to set
	 */
	public void setB(Integer b) {
		this.b = b;
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
}
