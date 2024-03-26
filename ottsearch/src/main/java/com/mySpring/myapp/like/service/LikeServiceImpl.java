package com.mySpring.myapp.like.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mySpring.myapp.content.dao.ContentDAO;
import com.mySpring.myapp.like.dao.LikeDAO;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeDAO likeDAO;
    
    @Autowired
    private ContentDAO contentDAO; 

    @Override
    public void addLike(int contentId, int userId) throws Exception {
        if (!existsLike(contentId, userId)) {
            likeDAO.insertLike(contentId, userId);
            // 좋아요 수 업데이트
            int likeCount = likeDAO.countLikes(contentId);
            contentDAO.updateLikes(contentId, likeCount);
        }
    }

    @Override
    public void removeLike(int contentId, int userId) throws Exception {
        if (existsLike(contentId, userId)) {
            likeDAO.deleteLike(contentId, userId);
            // 좋아요 수 업데이트
            int likeCount = likeDAO.countLikes(contentId);
            contentDAO.updateLikes(contentId, likeCount);
        }
    }

    @Override
    public int getLikeCount(int contentId) throws Exception {
        return likeDAO.countLikes(contentId);
    }

    @Override
    public boolean existsLike(int contentId, int userId) throws Exception {
        return likeDAO.existsLike(contentId, userId);
    }
}