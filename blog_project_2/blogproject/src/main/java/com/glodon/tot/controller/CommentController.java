package com.glodon.tot.controller;

import com.glodon.tot.dto.CommentInsertInfo;
import com.glodon.tot.dto.CommentIsertProps;
import com.glodon.tot.dto.CommentQueryInfo;
import com.glodon.tot.models.Comment;
import com.glodon.tot.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedList;

@Controller
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 插入
     *
     *asfasdgfsdgfas
     *
     * 一条评论
     * 根据用户Id和blogId以及评论的内容
     * 向数据库插入一条评论
     */
    public CommentInsertInfo insertComment(CommentIsertProps commentIsertProps) {
        long blogId = commentIsertProps.getBlogId();
        long userId = commentIsertProps.getUserId();
        String content = commentIsertProps.getContent();
        Comment comment = new Comment();
        CommentInsertInfo cid = new CommentInsertInfo();

        comment.setBlogId(blogId);
        comment.setUserId(userId);
        comment.setContent(content);
        comment.setBlogId(System.currentTimeMillis());
        try {
            commentService.insertComment(comment);
        } catch (Exception e) {
            cid.setCode(201);
            cid.setMessage("评论失败");
        }
        cid.setCode(200);
        cid.setMessage("评论成功");
        return cid;
    }


    /**
     * 获取blog对应的所有评论
     * 查询条件blogId
     */
    public String SearchCommentofBlog(long blogId) {
        CommentQueryInfo cqf = new CommentQueryInfo();
        LinkedList<Comment> commentList = null;
        try {
            commentService.selectByBlogId(blogId);
            commentList = commentService.selectByBlogId(blogId);

        } catch (Exception e) {

        }
        cqf.setCode(200);
        cqf.setData(commentList);
        cqf.setMessage("已经获取了所有的comments");
        return null;
    }

    /**
     * 删除一条博客评论
     * 当用户id和评论者id相同，
     * 或者id和对应博文所有者id相同可以删除评论
     */
    public String DeleteCommentofBlog() {
        return null;
    }

}
