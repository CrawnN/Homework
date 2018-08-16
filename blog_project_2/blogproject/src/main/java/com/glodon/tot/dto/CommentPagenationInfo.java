package com.glodon.tot.dto;

import com.glodon.tot.models.Comment;

import java.util.List;

/**
 * pages:当前评论页数
 * rows:评论的实际行数
 */
public class CommentPagenationInfo {
    private List<Comment> commentList;
    private int pages;
    private int rows;
    private String message;

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public int getPages() {
        return pages;
    }

    public int getRows() {
        return rows;
    }

    public String getMessage() {
        return message;
    }
}
