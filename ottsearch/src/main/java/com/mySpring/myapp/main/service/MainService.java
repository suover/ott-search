package com.mySpring.myapp.main.service;

import java.util.List;

import com.mySpring.myapp.content.vo.ContentVO;

public interface MainService {
	List<ContentVO> popularContents() throws Exception;
	List<ContentVO> upcomingContents() throws Exception;
}
