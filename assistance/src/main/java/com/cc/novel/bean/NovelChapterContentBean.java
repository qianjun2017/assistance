package com.cc.novel.bean;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by yuanwenshu on 2018/8/13.
 */
@Table(name="t_novel_chapter_content")
public class NovelChapterContentBean extends BaseOrm<NovelChapterContentBean> implements BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7857800234533619821L;

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * 小说章节
     */
    private Long chapterId;

    /**
     * 小说章节内容
     */
    private byte[] content;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
