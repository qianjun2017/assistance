package com.cc.order.bean;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;

/**
 * Created by yuanwenshu on 2018/10/29.
 */
@Table(name="t_order")
public class OrderBean extends BaseOrm<OrderBean> implements BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4817001033260069735L;

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * 订单编号
     */
    private String code;

    /**
     * 下单会员
     */
    private Long leaguerId;

    /**
     * 下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 订单总额   = 应收金额   + 优惠金额
     */
    private Long total;
    
    /**
     * 应收金额
     */
    private Long receivable;
    
    /**
     * 实收金额   = 应收金额 - 退款金额
     */
    private Long receipts;
    
    /**
     * 优惠金额
     */
    private Long discount;
    
    /**
     * 退款金额
     */
    private Long refund;

    /**
     * 店铺
     */
    @Transient
    private Long shopId;

    /**
     * 店铺名称
     */
    @Transient
    private String shopName;

    /**
     * 卖家
     */
    @Transient
    private Long sellerId;

    /**
     * 卖家名称
     */
    @Transient
    private String sellerName;
    
    /**
     * 订单状态
     */
    private String status;

    /**
     * 收货地址
     */
    private Long addressId;

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
	 * 支付方式
	 */
	private String payMethod;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getLeaguerId() {
        return leaguerId;
    }

    public void setLeaguerId(Long leaguerId) {
        this.leaguerId = leaguerId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

	public Long getReceivable() {
		return receivable;
	}

	public void setReceivable(Long receivable) {
		this.receivable = receivable;
	}

	public Long getReceipts() {
		return receipts;
	}

	public void setReceipts(Long receipts) {
		this.receipts = receipts;
	}

	public Long getDiscount() {
		return discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public Long getRefund() {
		return refund;
	}

	public void setRefund(Long refund) {
		this.refund = refund;
	}

	public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    /**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
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

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
}
