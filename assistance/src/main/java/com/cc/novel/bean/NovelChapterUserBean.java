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
@Table(name="t_novel_chapter_user")
public class NovelChapterUserBean extends BaseOrm<NovelChapterUserBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8501631050063212492L;

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * 小说
     */
    private Long novelId;

    /**
     * 小说章节
     */
    private Long chapterId;

    /**
     * 用户
     */
    private Long userId;

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

    public Long getNovelId() {
        return novelId;
    }

    public void setNovelId(Long novelId) {
        this.novelId = novelId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
