package com.platform.aix.controller.pikai.common.request;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月29日 18:37
 */
public class PikaiTimelineContentReq {
    private String content;
    private String tag;
    private String title;
    private String userId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
