/**
 * 
 */
package com.cc.common.web;

/**
 * @author Administrator
 *
 */
public class Tree<T> {

	/**
	 * 返回状态
	 */
	private boolean success;
	/**
	 * 返回信息
	 */
	private String message;
	
	/**
	 * 树总层级
	 */
	private int level;
	
	/**
	 * 根节点
	 */
	private T root;
	/**
	 * 
	 */
	public Tree() {
		this.success = Boolean.FALSE;
	}
	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	/**
	 * @return the root
	 */
	public T getRoot() {
		return root;
	}
	/**
	 * @param root the root to set
	 */
	public void setRoot(T root) {
		this.root = root;
	}
}
