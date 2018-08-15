package com.glodon.tot.dto;

import com.glodon.tot.models.Comment;

import java.util.LinkedList;

public class CommentQueryInfo {
    int code;
    String message;
    LinkedList<Comment> data;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(LinkedList<Comment> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public LinkedList<Comment> getData() {
        return data;
    }
}
