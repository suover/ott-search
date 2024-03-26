package com.mySpring.myapp.like.service;

public interface LikeService {
    void addLike(int contentId, int userId) throws Exception;
    void removeLike(int contentId, int userId) throws Exception;
    int getLikeCount(int contentId) throws Exception;
    boolean existsLike(int contentId, int userId) throws Exception;
}
