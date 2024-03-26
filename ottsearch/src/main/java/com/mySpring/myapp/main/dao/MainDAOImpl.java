package com.mySpring.myapp.main.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.mySpring.myapp.content.vo.ContentVO;

@Repository
public class MainDAOImpl implements MainDAO{
	
	@Autowired
    private SqlSession sqlSession;
	
	public List<ContentVO> popularContents() throws Exception {
		return sqlSession.selectList("mapper.main.popularList");
	};
	
	public List<ContentVO> upcomingContents() throws Exception {
		return sqlSession.selectList("mapper.main.upcomingList");
	};
	
}
