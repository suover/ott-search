package com.mySpring.myapp.content.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.mySpring.myapp.content.dao.ContentDAO;
import com.mySpring.myapp.content.vo.ActorVO;
import com.mySpring.myapp.content.vo.ContentVO;
import com.mySpring.myapp.content.vo.GenreVO;
import com.mySpring.myapp.content.vo.OttVO;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentDAO contentDAO;

    @Override
    public void write(ContentVO contentVO) throws Exception {
        contentDAO.write(contentVO);
    }
    
    @Override
    public List<ContentVO> getAllContents() throws Exception {
        return contentDAO.getAllContents();
    }
    
    @Override
    public ContentVO getContentById(int contentId) throws Exception {
        return contentDAO.getContentById(contentId);
    }
    
    @Override
    public void updateContent(ContentVO contentVO) throws Exception {
        contentDAO.updateContent(contentVO);
    }
    
    @Override
    public List<GenreVO> getAllGenres() throws Exception {
        return contentDAO.getAllGenres();
    }

    @Override
    public List<OttVO> getAllOtts() throws Exception {
        return contentDAO.getAllOtts();
    }
    
    @Override
    public List<ActorVO> getAllActors() throws Exception {
        return contentDAO.getAllActors();
    }
    
    @Override
    public void deleteContent(int contentId) throws Exception {
        contentDAO.deleteContent(contentId);
    }
    
    @Override
    public void updateContentHits(int contentId) throws Exception {
        contentDAO.updateContentHits(contentId);
    }
    
    @Override
    public List<ContentVO> getAllContentsWithPaging(int startRow, int endRow) throws Exception {
        return contentDAO.getAllContentsWithPaging(startRow, endRow);
    }

    @Override
    public int countContents() throws Exception {
        return contentDAO.countContents();
    }
    
    @Override
    public boolean isTitleUnique(String title) throws Exception {
        return contentDAO.isTitleUnique(title, null);
    }

    @Override
    public boolean isTitleUnique(String title, Integer contentId) throws Exception {
        return contentDAO.isTitleUnique(title, contentId);
    }
}