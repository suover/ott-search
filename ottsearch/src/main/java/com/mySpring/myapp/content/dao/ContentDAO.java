package com.mySpring.myapp.content.dao;

import java.util.List;

import com.mySpring.myapp.content.vo.ActorVO;
import com.mySpring.myapp.content.vo.ContentVO;
import com.mySpring.myapp.content.vo.GenreVO;
import com.mySpring.myapp.content.vo.OttVO;

public interface ContentDAO {
    public void write(ContentVO contentVO) throws Exception;
    List<ContentVO> getAllContents() throws Exception;
    ContentVO getContentById(int contentId) throws Exception;
    void updateContent(ContentVO contentVO) throws Exception;
    List<GenreVO> getAllGenres() throws Exception;
    List<OttVO> getAllOtts() throws Exception;
    List<ActorVO> getAllActors() throws Exception;
    void deleteContent(int contentId) throws Exception;
    void updateContentHits(int contentId) throws Exception;
    List<ContentVO> getAllContentsWithPaging(int startRow, int endRow) throws Exception;
    int countContents() throws Exception;
    boolean isTitleUnique(String title, Integer contentId) throws Exception;
    void updateLikes(int contentId, int likeCount);
}
