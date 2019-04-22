/**
 * 
 */
package com.cc.novel.http.response.data;

/**
 * @author Administrator
 *
 */
public class NovelChapterData {

	/**
	 * 目标网站主键
	 */
	private String id;
	
	/**
	 * 章节名称
	 */
	private String name;
	
	/**
	 * 小说内容
	 */
	private String content;
	
	/**
	 * 小说
	 */
	private String novel;
	
	/**
	 * 上一章节
	 */
	private String pre;
	
	/**
	 * 下一章节
	 */
	private String next;
	
	/**
	 * 爬虫编码
	 */
	private String spiderNo;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
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
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the novel
	 */
	public String getNovel() {
		return novel;
	}

	/**
	 * @param novel the novel to set
	 */
	public void setNovel(String novel) {
		this.novel = novel;
	}

	/**
	 * @return the pre
	 */
	public String getPre() {
		return pre;
	}

	/**
	 * @param pre the pre to set
	 */
	public void setPre(String pre) {
		this.pre = pre;
	}

	/**
	 * @return the next
	 */
	public String getNext() {
		return next;
	}

	/**
	 * @param next the next to set
	 */
	public void setNext(String next) {
		this.next = next;
	}

	/**
	 * @return the spiderNo
	 */
	public String getSpiderNo() {
		return spiderNo;
	}

	/**
	 * @param spiderNo the spiderNo to set
	 */
	public void setSpiderNo(String spiderNo) {
		this.spiderNo = spiderNo;
	}
}
