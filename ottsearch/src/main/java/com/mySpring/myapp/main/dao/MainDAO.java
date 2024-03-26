package com.mySpring.myapp.main.dao;

import java.util.List;

import com.mySpring.myapp.content.vo.ContentVO;

public interface MainDAO {
	List<ContentVO> popularContents() throws Exception;
	List<ContentVO> upcomingContents() throws Exception;
}
