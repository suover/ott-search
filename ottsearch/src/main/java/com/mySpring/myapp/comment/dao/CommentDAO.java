package com.mySpring.myapp.comment.dao;

import java.util.List;

import com.mySpring.myapp.comment.vo.CommentVO;

public interface CommentDAO {
    void insertComment(CommentVO comment) throws Exception;
    void deleteComment(int commentId) throws Exception;
    CommentVO selectComment(int commentId) throws Exception;
    List<CommentVO> selectCommentsByContentId(int contentId) throws Exception;
    void updateComment(CommentVO comment) throws Exception;
}