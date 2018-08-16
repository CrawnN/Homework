package com.glodon.tot.dto;

import com.glodon.tot.models.Comment;

public class DeleteCommentInfo {
    private String msg;
    private Comment data;
    private int code;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(Comment data) {
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public Comment getData() {
        return data;
    }

    public int getCode() {
        return code;
    }
}
