package com.glodon.tot.dto;

public class SelectBlogProps {
    private long id;
    private int offset;
    private int limit;

    public long getId() {
        return id;
    }

    public void setId(long blogId) {
        this.id = blogId;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
