package com.glodon.tot.service;

import com.glodon.tot.mappers.CommentMapper;
import com.glodon.tot.models.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    /**
     * 获取评论，根据博客的id
     *
     * @param blogId
     * @return
     */
    public LinkedList<Comment> selectByBlogId(long blogId) {
        LinkedList<Comment> commentList = null;
        commentList = commentMapper.selectByBlogId(blogId);
        return commentList;
    }

    /**
     * 插入一条评论品论记录
     *
     * @param comment
     * @return 返回数据库受影响函数，1表示成功，0表示插入失败
     */
    public int insertComment(Comment comment) {
        return commentMapper.insert(comment);
    }

    /**
     * 删除评论，根据博客id和评论人id
     * 例如传入blogId userId 时根据两个条件删除
     * 例如传入blogId，null仅根据blogId删除
     * 例如传入null，userId仅根据blogId删除
     * 传入两个都是null会删除整张comment表，但方法中阻止了这种行为
     *
     * @param blogId 博客id
     * @param userId 评论人id
     * @return 返回受影响行数
     */
    public int deleteComment(Long blogId, Long userId) {
        int i = 0;
        if (blogId != null || userId != null) {
            i = commentMapper.deleteByBlogIdOrUserId(blogId, userId);
        }
        return i;
    }


}
