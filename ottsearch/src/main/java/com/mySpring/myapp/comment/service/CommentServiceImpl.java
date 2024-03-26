package com.mySpring.myapp.comment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mySpring.myapp.comment.dao.CommentDAO;
import com.mySpring.myapp.comment.vo.CommentVO;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDAO commentDAO;

    @Override
    public void addComment(CommentVO commentVO) throws Exception {
        commentDAO.insertComment(commentVO);
    }

    @Override
    public List<CommentVO> getCommentsByContentId(int contentId) throws Exception {
        return commentDAO.selectCommentsByContentId(contentId);
    }

    @Override
    public void updateComment(CommentVO commentVO) throws Exception {
        commentDAO.updateComment(commentVO);
    }

    @Override
    public void deleteComment(int commentId) throws Exception {
        commentDAO.deleteComment(commentId);
    }
}
