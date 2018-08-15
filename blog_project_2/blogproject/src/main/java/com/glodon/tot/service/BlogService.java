package com.glodon.tot.service;

import com.glodon.tot.mappers.BlogMapper;
import com.glodon.tot.models.Blog;
import org.springframework.beans.factory.annotation.Autowired;

public class BlogService {
    @Autowired
    BlogMapper blogMapper;

    public int insertBlog(Blog blog) {
        return blogMapper.insert(blog);
    }

    public Blog selectBlogById(long blogId) {
        return blogMapper.selectByPrimaryKey(blogId);
    }

    public int deleteBlog(long blogId) {
        return blogMapper.deleteByPrimaryKey(blogId);
    }

    public int updateBlog(Blog blog) {
        return blogMapper.updateByPrimaryKey(blog);
    }
}
