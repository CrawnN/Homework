package com.glodon.tot.service;

import com.glodon.tot.mappers.BlogMapper;
import com.glodon.tot.models.Blog;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    @Autowired
    BlogMapper blogMapper;

    public int insertBlog(Blog blog) {
        return blogMapper.insert(blog);
    }

    public Blog selectBlogById(long blogId) {
        blogMapper.updataBlogReadTime(blogId);
        return blogMapper.selectByPrimaryKey(blogId);
    }

    public int deleteBlog(long blogId) {
        return blogMapper.deleteByPrimaryKey(blogId);
    }

    public int updateBlog(Blog blog) {
        return blogMapper.updateByPrimaryKey(blog);
    }

    public List<Blog> selectBlogsByUserId(long id, int offset, int limit) {
        RowBounds rowBounds = new RowBounds(offset,limit);
        return blogMapper.selectBlogsByUserId(id, rowBounds);
    }

    public List<Blog> selectHotBlog(int blogNum) {
        return blogMapper.selectHotBlog(blogNum);
    }
}
