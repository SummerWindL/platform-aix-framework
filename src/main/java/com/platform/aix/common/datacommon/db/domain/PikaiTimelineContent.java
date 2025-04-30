package com.platform.aix.common.datacommon.db.domain;

import com.platform.aix.common.datacommon.db.KeyMethodInterface;
import com.platform.aix.service.pikai.PikaiTimelineContentInterface;
import com.platform.core.base.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class PikaiTimelineContent extends BaseEntity implements Serializable, PikaiTimelineContentInterface, Cloneable, KeyMethodInterface<Integer> {
    private Integer id;

    private String title;

    private String content;

    private String tag;

    private String userId;

    private Object imgId;

    private Object videoId;

    private Date createTime;

    private Date updateTime;

    // 新增字段写在下面
    private String contentUid;

    public String getContentUid() {
        return contentUid;
    }

    public void setContentUid(String contentUid) {
        this.contentUid = contentUid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Object getImgId() {
        return imgId;
    }

    public void setImgId(Object imgId) {
        this.imgId = imgId;
    }

    public Object getVideoId() {
        return videoId;
    }

    public void setVideoId(Object videoId) {
        this.videoId = videoId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}