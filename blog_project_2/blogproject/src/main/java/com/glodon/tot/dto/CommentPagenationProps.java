package com.glodon.tot.dto;

/**
 * pages:当前评论页数
 * rows:评论的实际行数
 */
public class CommentPagenationProps {
    private long blogId;
    private int pages;
    private int rows;

    public void setBlogId(long blogId) {
        this.blogId = blogId;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public long getBlogId() {
        return blogId;
    }

    public int getPages() {
        return pages;
    }

    public int getRows() {
        return rows;
    }
}
