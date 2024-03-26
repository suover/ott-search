package com.mySpring.myapp.search.sevice;

import java.util.List;

import com.mySpring.myapp.content.vo.ContentVO;

public interface SearchService {
    int countSearchList(String searchType, String keyword) throws Exception;
    List<ContentVO> searchListPaging(String searchType, String keyword, int startRow, int endRow) throws Exception;
}
