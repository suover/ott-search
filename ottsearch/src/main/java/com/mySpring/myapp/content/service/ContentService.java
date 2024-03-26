package com.mySpring.myapp.content.service;

import java.util.List;

import com.mySpring.myapp.content.vo.ActorVO;
import com.mySpring.myapp.content.vo.ContentVO;
import com.mySpring.myapp.content.vo.GenreVO;
import com.mySpring.myapp.content.vo.OttVO;

public interface ContentService {
    public void write(ContentVO contentVO) throws Exception;
    public List<ContentVO> getAllContents() throws Exception;
    ContentVO getContentById(int contentId) throws Exception;
    public void updateContent(ContentVO contentVO) throws Exception;
    public List<GenreVO> getAllGenres() throws Exception;
    public List<OttVO> getAllOtts() throws Exception;
    List<ActorVO> getAllActors() throws Exception;
    void deleteContent(int contentId) throws Exception;
    void updateContentHits(int contentId) throws Exception;
    List<ContentVO> getAllContentsWithPaging(int startRow, int endRow) throws Exception;
    int countContents() throws Exception;
    boolean isTitleUnique(String title) throws Exception;
    boolean isTitleUnique(String title, Integer contentId) throws Exception;
}
