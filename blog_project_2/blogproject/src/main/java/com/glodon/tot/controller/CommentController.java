package com.glodon.tot.controller;

import com.glodon.tot.dto.*;
import com.glodon.tot.models.Blog;
import com.glodon.tot.models.Comment;
import com.glodon.tot.service.BlogService;
import com.glodon.tot.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;

    /**
     * 插入
     * <p>
     * asfasdgfsdgfas
     * <p>
     * 一条评论
     * 根据用户Id和blogId以及评论的内容
     * 向数据库插入一条评论
     */
    @PostMapping("/insertcomment")
    public CommentInsertInfo insertComment(CommentInsertProps commentInsertProps) {
        long blogId = commentInsertProps.getBlogId();
        long userId = commentInsertProps.getUserId();
        String content = commentInsertProps.getContent();
        Comment comment = new Comment();
        CommentInsertInfo cid = new CommentInsertInfo();

        comment.setBlogId(blogId);
        comment.setUserId(userId);
        comment.setContent(content);
        {
            int i = 0;
            i = commentService.insertComment(comment);
            if (i == 0) {
                cid.setCode(201);
                cid.setMessage("评论失败");
            }
        }
        cid.setCode(200);
        cid.setMessage("评论成功");
        cid.setData(comment);
        return cid;
    }


    /**
     * 获取blog对应的所有评论
     * 查询条件blogId
     */
    @PostMapping("/searchcommentofblog")
    public CommentQueryInfo searchCommentofBlog(long blogId) {
        System.out.println(blogId);
        CommentQueryInfo cqf = new CommentQueryInfo();
        LinkedList<Comment> commentList = null;
        try {
            commentService.selectByBlogId(blogId);
            commentList = commentService.selectByBlogId(blogId);
        } catch (Exception e) {
            cqf.setMessage("获取comments失败");
        }
        if (commentList == null || commentList.size() == 0) {
            cqf.setMessage("没有comment");
        } else {

        }
        cqf.setCode(200);
        cqf.setData(commentList);
        System.out.println(commentList);
        cqf.setMessage("已经获取了所有的comments");
        return cqf;
    }

    /**
     * 删除一条博客，根据删除博客的
     * User的userId和博客的blogId
     * 确定是否能够删除
     * 根据commentId查找comment
     * 1、删除人是comment写作人可以删除
     * 2、当userId是comment所属博文的作者，可以删除
     *
     * @param userId    执行删除功能的User的userId
     * @param commentId 即将被删除的commentId
     * @return
     */
    @PostMapping("/deleteonecomment")
    public DeleteCommentInfo deleteOneCommentofBlog(long userId, long commentId) {
        Comment comment = commentService.findCommentByCommentId(commentId);
        boolean canDelete = false;
        DeleteCommentInfo dci = new DeleteCommentInfo();
        if (userId == comment.getUserId()) {
            canDelete = true;
        } else {
            long blogId = comment.getBlogId();
            Blog blog = blogService.selectBlogById(blogId);
            if (blog == null || userId == blog.getUserId())
                canDelete = true;
        }
        if (canDelete) {

            commentService.deleteCommentById(commentId);
            dci.setData(comment);
            dci.setMsg("成功删除评论");
            dci.setCode(200);
        } else {
            dci.setCode(201);
            dci.setMsg("删除失败");
        }
        return dci;
    }


    /**
     * 评论分页加载
     * @param commentPagenationProps 此参数中的pages为当前页数！！！
     * @return
     */
    @PostMapping("/pagenatecomment")
    public CommentPagenationInfo paginateComment(CommentPagenationProps commentPagenationProps) {
        long blogId = commentPagenationProps.getBlogId();
        int pages = commentPagenationProps.getPages();
        int rows = commentPagenationProps.getRows();
        //讲页数转换为查询的offset偏移量
        LinkedList<Comment> commentLinkedList =
                commentService.selectSomeCommentsOfBlog(blogId, (pages - 1) * rows, rows);
        CommentPagenationInfo cpi = new CommentPagenationInfo();
        int realrows = 0;
        if (commentLinkedList == null || commentLinkedList.size() == 0) {
            cpi.setMessage("没有评论了");
        } else {
            realrows = commentLinkedList.size();
            cpi.setMessage("评论加载成功");
        }
        cpi.setCommentList(commentLinkedList);
        cpi.setRows(realrows);
        cpi.setPages(pages);
        return cpi;
    }

    /**
     * 测试用
     *
     * @param blogId
     * @param userId
     * @return
     */
    @PostMapping("/deletecomment")
    public DeleteCommentInfo deleteCommentofId(long blogId, long userId) {
        int i = commentService.deleteComment(blogId, null);
        int i1 = commentService.deleteComment(null, userId);
        int i2 = commentService.deleteComment(blogId, userId);
        DeleteCommentInfo dci = new DeleteCommentInfo();
        dci.setCode(i2);
        return dci;
    }


}
