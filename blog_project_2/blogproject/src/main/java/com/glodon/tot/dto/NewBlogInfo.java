package com.glodon.tot.dto;

public class NewBlogInfo {
    private int code;
    private String message;
    private com.glodon.tot.dto.NewBlogProps newBlogProps;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NewBlogProps getNewBlogProps() {
        return newBlogProps;
    }

    public void setNewBlogProps(NewBlogProps newBlogProps) {
        this.newBlogProps = newBlogProps;
    }
}
