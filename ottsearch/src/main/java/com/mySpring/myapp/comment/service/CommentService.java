package com.mySpring.myapp.comment.service;

import java.util.List;

import com.mySpring.myapp.comment.vo.CommentVO;

public interface CommentService {

    void addComment(CommentVO commentVO) throws Exception;
    List<CommentVO> getCommentsByContentId(int contentId) throws Exception;
    void updateComment(CommentVO commentVO) throws Exception;
    void deleteComment(int commentId) throws Exception;
}
