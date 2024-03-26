package com.mySpring.myapp.comment.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mySpring.myapp.comment.vo.CommentVO;

@Repository
public class CommentDAOImpl implements CommentDAO {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public void insertComment(CommentVO comment) throws Exception {
        sqlSession.insert("mapper.comment.insertComment", comment);
    }

    @Override
    public void deleteComment(int commentId) throws Exception {
        sqlSession.delete("mapper.comment.deleteComment", commentId);
    }

    @Override
    public CommentVO selectComment(int commentId) throws Exception {
        return sqlSession.selectOne("mapper.comment.selectComment", commentId);
    }

    @Override
    public List<CommentVO> selectCommentsByContentId(int contentId) throws Exception {
        return sqlSession.selectList("mapper.comment.selectCommentsByContentId", contentId);
    }

    @Override
    public void updateComment(CommentVO comment) throws Exception {
        sqlSession.update("mapper.comment.updateComment", comment);
    }
}