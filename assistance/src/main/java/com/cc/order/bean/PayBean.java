package com.cc.order.bean;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by yuanwenshu on 2018/10/29.
 */
@Table(name="t_pay")
public class PayBean extends BaseOrm<PayBean> implements BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1648244816307219932L;

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
	
	/**
	 * 订单
	 */
	private Long orderId;
	
	/**
	 * 子订单
	 */
	private Long orderSubId;

	/**
     * 支付流水号
     */
    private String code;

    /**
     * 外部流水号
     */
    private String serial;

    /**
     * 应收金额  = 实收金额  + 退款金额
     */
    private Long receivable;
    
    /**
     * 实收金额
     */
    private Long receipts;
    
    /**
     * 退款金额
     */
    private Long refund;

    /**
     * 支付账户
     */
    private String fromAccount;

    /**
     * 收款账户
     */
    private String toAccount;

    /**
     * 支付状态
     */
    private String status;

    /**
     * 发起支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    /**
     * 完成支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;
    
    /**
     * 支付方式
     */
    private String method;
    
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	/**
	 * @return the orderId
	 */
	public Long getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the orderSubId
	 */
	public Long getOrderSubId() {
		return orderSubId;
	}

	/**
	 * @param orderSubId the orderSubId to set
	 */
	public void setOrderSubId(Long orderSubId) {
		this.orderSubId = orderSubId;
	}

	public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

	/**
	 * @return the receivable
	 */
	public Long getReceivable() {
		return receivable;
	}

	/**
	 * @param receivable the receivable to set
	 */
	public void setReceivable(Long receivable) {
		this.receivable = receivable;
	}

	/**
	 * @return the receipts
	 */
	public Long getReceipts() {
		return receipts;
	}

	/**
	 * @param receipts the receipts to set
	 */
	public void setReceipts(Long receipts) {
		this.receipts = receipts;
	}

	/**
	 * @return the refund
	 */
	public Long getRefund() {
		return refund;
	}

	/**
	 * @param refund the refund to set
	 */
	public void setRefund(Long refund) {
		this.refund = refund;
	}

	/**
	 * @return the fromAccount
	 */
	public String getFromAccount() {
		return fromAccount;
	}

	/**
	 * @param fromAccount the fromAccount to set
	 */
	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}

	/**
	 * @return the toAccount
	 */
	public String getToAccount() {
		return toAccount;
	}

	/**
	 * @param toAccount the toAccount to set
	 */
	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}

	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	/**
	 * @return the payTime
	 */
	public Date getPayTime() {
		return payTime;
	}

	/**
	 * @param payTime the payTime to set
	 */
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}
}
