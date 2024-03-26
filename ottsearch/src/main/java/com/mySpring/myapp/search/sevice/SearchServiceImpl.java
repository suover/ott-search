package com.mySpring.myapp.search.sevice;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.mySpring.myapp.content.vo.ContentVO;
import com.mySpring.myapp.search.dao.SearchDAO;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SearchServiceImpl implements SearchService {
	@Autowired
	private SearchDAO searchDAO;
	   
    @Override
    public int countSearchList(String searchType, String keyword) throws Exception {
    	return searchDAO.countSearchList(searchType, keyword);
    }
    
    @Override
    public List<ContentVO> searchListPaging(String searchType, String keyword, int startRow, int endRow) throws Exception {
    	return searchDAO.searchListPaging(searchType, keyword, startRow, endRow);
    }
}

