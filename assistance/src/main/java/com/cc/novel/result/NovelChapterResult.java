package com.cc.novel.result;

/**
 * Created by yuanwenshu on 2018/8/6.
 */
public class NovelChapterResult {

    /**
     * 小说章节
     */
    private Long id;

    /**
     * 小说章节名称
     */
    private String name;

    /**
     * 小说章节内容
     */
    private String content;

    /**
     * 小说
     */
    private Long novelId;

    /**
     * 上一章节
     */
    private Long preId;

    /**
     * 上一章节名称
     */
    private String preName;

    /**
     * 下一章节
     */
    private Long nextId;

    /**
     * 下一章节名称
     */
    private String nextName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPreId() {
        return preId;
    }

    public void setPreId(Long preId) {
        this.preId = preId;
    }

    public String getPreName() {
        return preName;
    }

    public void setPreName(String preName) {
        this.preName = preName;
    }

    public Long getNextId() {
        return nextId;
    }

    public void setNextId(Long nextId) {
        this.nextId = nextId;
    }

    public String getNextName() {
        return nextName;
    }

    public void setNextName(String nextName) {
        this.nextName = nextName;
    }

    public Long getNovelId() {
        return novelId;
    }

    public void setNovelId(Long novelId) {
        this.novelId = novelId;
    }
}
