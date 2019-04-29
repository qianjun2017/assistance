package com.cc.seller.form;

import com.cc.common.form.QueryForm;

/**
 * Created by yuanwenshu on 2018/10/23.
 */
public class SellerQueryForm extends QueryForm {

    /**
     * 卖家名称/真实姓名
     */
    private String name;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 卖家状态
     */
    private String status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
