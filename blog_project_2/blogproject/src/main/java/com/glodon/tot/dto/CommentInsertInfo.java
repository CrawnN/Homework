package com.glodon.tot.dto;

import com.glodon.tot.models.Comment;

public class CommentInsertInfo {
    private int code;
    private String message;
    private Comment data;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Comment data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Comment getData() {
        return data;
    }

}
