package com.glodon.tot.controller;

import com.glodon.tot.dto.*;
import com.glodon.tot.models.Blog;
import com.glodon.tot.models.Comment;
import com.glodon.tot.service.BlogService;
import com.glodon.tot.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sun.misc.Request;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BlogController {
    @Autowired
    BlogService blogService;
    @Autowired
    CommentService commentService;

    //新建博客
    @RequestMapping(value = "/newblog", method = RequestMethod.POST)
    @Transactional
    public Object newBlog(@RequestBody NewBlogProps newBlogProps) {
        Blog blog = new Blog();
        blog.setUserId(newBlogProps.getUserId());
        blog.setTitle(newBlogProps.getTitle());
        blog.setKeyword(newBlogProps.getKeyword());
        blog.setContent(newBlogProps.getContent());
        System.out.println(blog.getContent());
        blogService.insertBlog(blog);
        return blog;
    }

    //删除博客以及该博客的所有评论
    @RequestMapping(value = "/deleteblog", method = RequestMethod.POST)
    @Transactional
    public long deletelog(long blogId) {
        System.out.println(blogId);
        commentService.deleteComment(blogId, null);
        blogService.deleteBlog(blogId);
        return blogId;
    }

    //更新博客
    @RequestMapping(value = "/updateblog", method = RequestMethod.POST)
    @Transactional
    public Object updateBlog(@RequestBody Blog blog) {
        blogService.updateBlog(blog);
        return blog;
    }

    //根据博客ID返回此条博客以及该博客的所有评论，评论分页返回
    @RequestMapping(value = "/selectblog", method = RequestMethod.POST)
    public BlogInfo selectBlogById(@RequestBody SelectBlogProps selectBlogProps) {
        Blog blog = blogService.selectBlogById(selectBlogProps.getId());
        List<Comment> comments = commentService.selectSomeCommentsOfBlog(selectBlogProps.getId(),selectBlogProps.getOffset(),selectBlogProps.getLimit());
        Collections.sort(comments, new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                return (int)(o1.getCommentTime().getTime() - o2.getCommentTime().getTime());
            }
        });
        BlogInfo blogInfo = new BlogInfo();
        blogInfo.setBlog(blog);
        blogInfo.setComments(comments);
        return blogInfo;
    }

    //根据用户ID返回该用户所有的博客，并进行分页返回
    @RequestMapping(value = "/selectuserblogs", method = RequestMethod.POST)
    public List<Blog> selectBlogsByUserId(@RequestBody  SelectBlogProps selectBlogProps) {
        List<Blog> blogs = blogService.selectBlogsByUserId(selectBlogProps.getId(),selectBlogProps.getOffset(),selectBlogProps.getLimit());
        return blogs;
    }

    /**
     * 返回访问量最大的博客列表
     * @param pageIndex 需要返回的热门博客熟练
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/selecthotblogs", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDate selectHotBlog(@RequestParam int pageIndex) {
        List<Blog> blogs = blogService.selectHotBlog(pageIndex);
        for(Blog blog: blogs){
            System.out.println(blog.getUpdateTime());
        }
        ResponseDate responseDate = new ResponseDate();
        responseDate.setCode(200);
        responseDate.setMessage("successful");
        responseDate.setData(blogs);
        return responseDate;
    }


}
