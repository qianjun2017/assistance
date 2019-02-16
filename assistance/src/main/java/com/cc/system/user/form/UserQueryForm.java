/**
 * 
 */
package com.cc.system.user.form;

import com.cc.common.tools.StringTools;

/**
 * @author Administrator
 *
 */
public class UserQueryForm {
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 用户昵称
	 */
	private String nickName;
	
	/**
	 * 真实姓名
	 */
	private String realName;
	
	/**
	 * 手机号码
	 */
	private String phone;

	/**
	 * 页码
	 */
	private String page = "1";
	
	/**
	 * 每页数量
	 */
	private String pageSize = "10";
	
	/**
	 * 排序字段
	 */
	private String sort;
	
	/**
	 * 排序方向
	 */
	private String order;
	
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		if(!StringTools.isNullOrNone(this.page) && StringTools.isNumber(this.page)){
			return Integer.parseInt(this.page);
		}
		return 1;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		if(!StringTools.isNullOrNone(this.pageSize) && StringTools.isNumber(this.pageSize)){
			return Integer.parseInt(this.pageSize);
		}
		return 10;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the sort
	 */
	public String getSort() {
		if(StringTools.isNullOrNone(this.sort)){
			return "createTime";
		}
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * @return the order
	 */
	public String getOrder() {
		if (StringTools.isNullOrNone(this.order)) {
			return "desc";
		}
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(String order) {
		this.order = order;
	}
}
