package com.cc.order.bean;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by yuanwenshu on 2018/10/29.
 */
@Table(name="t_address")
public class AddressBean extends BaseOrm<AddressBean> implements BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1548966892912556420L;

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * 会员
     */
    private Long leaguerId;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 收货人
     */
    private String receiver;

    /**
     * 收货人联系电话
     */
    private String mobile;

    /**
     * 收货人省
     */
    private Long provinceId;

    /**
     * 收货人市
     */
    private Long cityId;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLeaguerId() {
        return leaguerId;
    }

    public void setLeaguerId(Long leaguerId) {
        this.leaguerId = leaguerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
}
