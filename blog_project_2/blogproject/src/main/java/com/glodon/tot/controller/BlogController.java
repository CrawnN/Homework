package com.glodon.tot.controller;

import com.glodon.tot.dto.NewBlogProps;
import com.glodon.tot.mappers.BlogMapper;
import com.glodon.tot.models.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.Request;

@RestController
@RequestMapping("/api")
public class BlogController {
    @Autowired
    BlogMapper blogMapper;

    @RequestMapping(value = "/newblog", method = RequestMethod.POST)
    @Transactional
    public Blog newBlog(NewBlogProps newBlogProps) {
        Blog blog = new Blog();
        blog.setUserId(newBlogProps.getUserId());
        blog.setTitle(newBlogProps.getTitle());
        blog.setKeyword(newBlogProps.getKeyword());
        blog.setContent(newBlogProps.getContent());
        blogMapper.insert(blog);
        return blog;
    }

    @RequestMapping(value = "/deleteblog", method = RequestMethod.POST)
    @Transactional
    public long deletelog(Long blogId) {
        blogMapper.deleteByPrimaryKey(blogId);
        return blogId;
    }

    @RequestMapping(value = "/updateblog", method = RequestMethod.POST)
    @Transactional
    public Blog updateBlog(Blog blog) {
        blogMapper.updateByPrimaryKey(blog);
        return blog;
    }
}
