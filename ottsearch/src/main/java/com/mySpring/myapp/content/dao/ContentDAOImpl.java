package com.mySpring.myapp.content.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mySpring.myapp.content.vo.ActorVO;
import com.mySpring.myapp.content.vo.ContentVO;
import com.mySpring.myapp.content.vo.GenreVO;
import com.mySpring.myapp.content.vo.OttVO;

@Repository
public class ContentDAOImpl implements ContentDAO {

    @Autowired
    private SqlSession sqlSession;

    // 게시글 작성 구현
    @Override
    public void write(ContentVO contentVO) throws Exception {
        sqlSession.insert("mapper.content.insertNewContent", contentVO);

        // 장르 처리
        for (Integer genreId : contentVO.getGenreIds()) {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("contentId", contentVO.getContentId());
            paramMap.put("genreId", genreId);
            sqlSession.insert("mapper.content.insertContentGenre", paramMap);
        }

        // 배우 처리
        for (String actorName : contentVO.getActorNames()) {
            Map<String, Object> actorMap = new HashMap<>();
            actorMap.put("actorName", actorName);
            sqlSession.insert("mapper.content.insertActorIfNotExists", actorMap);

            Map<String, Object> contentActorMap = new HashMap<>();
            contentActorMap.put("contentId", contentVO.getContentId());
            contentActorMap.put("actorName", actorName);
            sqlSession.insert("mapper.content.insertContentActor", contentActorMap);
        }

        // OTT 채널 처리
        for (Integer ottId : contentVO.getOttIds()) {
            Map<String, Object> ottMap = new HashMap<>();
            ottMap.put("contentId", contentVO.getContentId());
            ottMap.put("ottId", ottId);
            sqlSession.insert("mapper.content.insertContentOtt", ottMap);
        }
    }
	    @Override
	    public List<ContentVO> getAllContents() throws Exception {
	        return sqlSession.selectList("mapper.content.getAllContents");
	    }
	    
	    @Override
	    public ContentVO getContentById(int contentId) throws Exception {
	        ContentVO content = sqlSession.selectOne("mapper.content.selectContentById", contentId);
	        List<Integer> genreIds = sqlSession.selectList("mapper.content.selectGenreIdsByContentId", contentId);
	        List<Integer> ottIds = sqlSession.selectList("mapper.content.selectOttIdsByContentId", contentId);
	        List<String> actorNames = sqlSession.selectList("mapper.content.selectActorNamesByContentId", contentId);

	        content.setGenreIds(genreIds);
	        content.setOttIds(ottIds);
	        content.setActorNames(actorNames);

	        return content;
	    }
	    
	    @Override
	    public void updateContent(ContentVO contentVO) throws Exception {
	        sqlSession.update("mapper.content.updateContent", contentVO);

	        updateGenres(contentVO);
	        updateActors(contentVO);
	        updateOtts(contentVO);
	    }

	    private void updateGenres(ContentVO contentVO) {
	        List<Integer> existingGenreIds = sqlSession.selectList("mapper.content.selectExistingGenreIds", contentVO.getContentId());
	        for (Integer genreId : contentVO.getGenreIds()) {
	            if (!existingGenreIds.contains(genreId)) {
	                Map<String, Object> genreMap = new HashMap<>();
	                genreMap.put("contentId", contentVO.getContentId());
	                genreMap.put("genreId", genreId);
	                sqlSession.insert("mapper.content.insertContentGenre", genreMap);
	            }
	        }

	        for (Integer existingGenreId : existingGenreIds) {
	            if (!contentVO.getGenreIds().contains(existingGenreId)) {
	                Map<String, Object> genreMap = new HashMap<>();
	                genreMap.put("contentId", contentVO.getContentId());
	                genreMap.put("genreId", existingGenreId);
	                sqlSession.delete("mapper.content.deleteContentGenre", genreMap);
	            }
	        }
	    }

	    private void updateActors(ContentVO contentVO) {
	        List<String> existingActorNames = sqlSession.selectList("mapper.content.selectExistingActorNames", contentVO.getContentId());

	        // 새로 추가되는 배우 처리
	        for (String actorName : contentVO.getActorNames()) {
	            if (!existingActorNames.contains(actorName)) {
	                Map<String, Object> actorMap = new HashMap<>();
	                actorMap.put("actorName", actorName);
	                sqlSession.insert("mapper.content.insertActorIfNotExists", actorMap);

	                Map<String, Object> contentActorMap = new HashMap<>();
	                contentActorMap.put("contentId", contentVO.getContentId());
	                contentActorMap.put("actorName", actorName);
	                sqlSession.insert("mapper.content.insertContentActor", contentActorMap);
	            }
	        }

	        // 기존에 있던 배우 중 더 이상 연결되지 않은 배우 삭제
	        for (String existingActorName : existingActorNames) {
	            if (!contentVO.getActorNames().contains(existingActorName)) {
	                Map<String, Object> contentActorMap = new HashMap<>();
	                contentActorMap.put("contentId", contentVO.getContentId());
	                contentActorMap.put("actorName", existingActorName);
	                sqlSession.delete("mapper.content.deleteContentActor", contentActorMap);
	            }
	        }
	    }

	    private void updateOtts(ContentVO contentVO) {
	        List<Integer> existingOttIds = sqlSession.selectList("mapper.content.selectExistingOttIds", contentVO.getContentId());
	        for (Integer ottId : contentVO.getOttIds()) {
	            if (!existingOttIds.contains(ottId)) {
	                Map<String, Object> ottMap = new HashMap<>();
	                ottMap.put("contentId", contentVO.getContentId());
	                ottMap.put("ottId", ottId);
	                sqlSession.insert("mapper.content.insertContentOtt", ottMap);
	            }
	        }

	        for (Integer existingOttId : existingOttIds) {
	            if (!contentVO.getOttIds().contains(existingOttId)) {
	                Map<String, Object> ottMap = new HashMap<>();
	                ottMap.put("contentId", contentVO.getContentId());
	                ottMap.put("ottId", existingOttId);
	                sqlSession.delete("mapper.content.deleteContentOtt", ottMap);
	            }
	        }
	    }
	    
	    @Override
	    public List<GenreVO> getAllGenres() throws Exception {
	        return sqlSession.selectList("mapper.genre.getAllGenres");
	    }

	    @Override
	    public List<OttVO> getAllOtts() throws Exception {
	        return sqlSession.selectList("mapper.ott.getAllOtts");
	    }
	    
	    @Override
	    public List<ActorVO> getAllActors() throws Exception {
	        return sqlSession.selectList("mapper.actor.getAllActors");
	    }
	    
	    @Override
	    public void deleteContent(int contentId) throws Exception {
	        sqlSession.update("mapper.content.markContentAsDeleted", contentId);
	    }
	    
	    @Override
	    public void updateContentHits(int contentId) throws Exception {
	        sqlSession.update("mapper.content.updateContentHits", contentId);
	    }
	    
	    @Override
	    public List<ContentVO> getAllContentsWithPaging(int startRow, int endRow) throws Exception {
	        Map<String, Object> paramMap = new HashMap<>();
	        paramMap.put("startRow", startRow);
	        paramMap.put("endRow", endRow);
	        return sqlSession.selectList("mapper.content.getAllContentsWithPaging", paramMap);
	    }

	    @Override
	    public int countContents() throws Exception {
	        return sqlSession.selectOne("mapper.content.countContents");
	    }
	    
	    @Override
	    public boolean isTitleUnique(String title, Integer contentId) throws Exception {
	        Map<String, Object> params = new HashMap<>();
	        params.put("title", title);
	        params.put("contentId", contentId);
	        Integer count = sqlSession.selectOne("mapper.content.checkTitleExists", params);
	        return count != null && count == 0;
	    }
	    
	    @Override
	    public void updateLikes(int contentId, int likeCount) {
	        Map<String, Object> params = new HashMap<>();
	        params.put("contentId", contentId);
	        params.put("likeCount", likeCount);
	        sqlSession.update("mapper.content.updateLikes", params);
	    }
}