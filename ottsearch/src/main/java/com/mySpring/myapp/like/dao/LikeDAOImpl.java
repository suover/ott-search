package com.mySpring.myapp.like.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LikeDAOImpl implements LikeDAO {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public void insertLike(int contentId, int userId) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("contentId", contentId);
        params.put("userId", userId);
        sqlSession.insert("mapper.like.insertLike", params);
    }

    @Override
    public void deleteLike(int contentId, int userId) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("contentId", contentId);
        params.put("userId", userId);
        sqlSession.delete("mapper.like.deleteLike", params);
    }

    @Override
    public int countLikes(int contentId) throws Exception {
        // 로직: content_id를 사용하여 해당 컨텐츠의 좋아요 수를 반환
        return sqlSession.selectOne("mapper.like.countLikes", contentId);
    }

    @Override
    public boolean existsLike(int contentId, int userId) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("contentId", contentId);
        params.put("userId", userId);

        // 결과가 정수로 반환됩니다.
        Integer result = sqlSession.selectOne("mapper.like.existsLike", params);
        // 정수 결과를 불린 값으로 변환합니다.
        return result != null && result > 0;
    }
}