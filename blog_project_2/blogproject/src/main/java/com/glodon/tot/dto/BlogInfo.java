package com.glodon.tot.dto;

import com.glodon.tot.models.Blog;
import com.glodon.tot.models.Comment;

import java.util.List;

public class BlogInfo {
    private Blog blog;
    private List<Comment> comments;

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
