/**
 * 
 */
package com.cc.interfaces.bean;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;

/**
 * @author ws_yu
 *
 */
@Table(name="t_interface")
public class InterfaceBean extends BaseOrm<InterfaceBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8298342384537305384L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 接口名称
	 */
	private String name;
	
	/**
	 * 接口代码
	 */
	private String code;
	
	/**
	 * 接口类型
	 */
	private String type;
	
	/**
	 * 请求报文
	 */
	private byte[] request;
	
	/**
	 * 响应报文
	 */
	private byte[] response;
	
	/**
	 * 接口调用时间
	 */
	private Date createTime;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the request
	 */
	public byte[] getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(byte[] request) {
		this.request = request;
	}

	/**
	 * @return the response
	 */
	public byte[] getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(byte[] response) {
		this.response = response;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
