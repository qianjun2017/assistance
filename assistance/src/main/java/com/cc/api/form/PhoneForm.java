package com.cc.api.form;

public class PhoneForm {
	
	/**
	 * 会员
	 */
	private Long leaguerId;
	
	/**
	 * 
	 */
	private String code;
	
	/**
	 * 手机号码加密数据
	 */
	private String encryptedData;
	
	/**
	 * 手机号码加密数据初始向量
	 */
	private String iv;

	/**
	 * @return the leaguerId
	 */
	public Long getLeaguerId() {
		return leaguerId;
	}

	/**
	 * @param leaguerId the leaguerId to set
	 */
	public void setLeaguerId(Long leaguerId) {
		this.leaguerId = leaguerId;
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
	 * @return the encryptedData
	 */
	public String getEncryptedData() {
		return encryptedData;
	}

	/**
	 * @param encryptedData the encryptedData to set
	 */
	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}

	/**
	 * @return the iv
	 */
	public String getIv() {
		return iv;
	}

	/**
	 * @param iv the iv to set
	 */
	public void setIv(String iv) {
		this.iv = iv;
	}

}
