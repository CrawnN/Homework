package com.glodon.tot.dto;

public class CommentIsertProps {
    private long blogId;
    private long userId;
    private String content;

    public void setBlogId(long blogId) {
        this.blogId = blogId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getBlogId() {
        return blogId;
    }

    public long getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }
}
