package com.mySpring.myapp.search.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.mySpring.myapp.content.vo.ContentVO;

@Repository
public class SearchDAOImpl implements SearchDAO {
	
	@Autowired
    private SqlSession sqlSession;
	
	// 검색 결과 카운트 쿼리 매핑
	@Override
	public int countSearchList(String searchType, String keyword) throws Exception {

		if (searchType.equals("1")) {
			return sqlSession.selectOne("mapper.search.countSearchByTitle", keyword);
		} else if (searchType.equals("2")) {
			return sqlSession.selectOne("mapper.search.countSearchByGenre", keyword);
		} else {
			return sqlSession.selectOne("mapper.search.countSearchByOtt", keyword);
		}
	}
	
    @Override
    public List<ContentVO> searchListPaging(String searchType, String keyword, int startRow, int endRow) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("startRow", startRow);
        param.put("endRow", endRow);
        param.put("keyword", keyword);
        
		if (searchType.equals("1")) {
			return sqlSession.selectList("mapper.search.searchByTitlePaging", param);
		} else if (searchType.equals("2")) {
			return sqlSession.selectList("mapper.search.searchByGenrePaging", param);
		} else {
			return sqlSession.selectList("mapper.search.searchByOttPaging", param);
		}
    }

}
