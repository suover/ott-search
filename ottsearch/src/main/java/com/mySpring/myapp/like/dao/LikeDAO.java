package com.mySpring.myapp.like.dao;

public interface LikeDAO {
    void insertLike(int contentId, int userId) throws Exception;
    void deleteLike(int contentId, int userId) throws Exception;
    int countLikes(int contentId) throws Exception;
    boolean existsLike(int contentId, int userId) throws Exception;
}