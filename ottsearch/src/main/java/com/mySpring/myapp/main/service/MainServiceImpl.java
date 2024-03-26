package com.mySpring.myapp.main.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.mySpring.myapp.content.vo.ContentVO;
import com.mySpring.myapp.main.dao.MainDAO;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MainServiceImpl implements MainService {
	
	@Autowired
	private MainDAO mainDAO;
	
	public List<ContentVO> popularContents() throws Exception {
		return mainDAO.popularContents();
	};
	
	public List<ContentVO> upcomingContents() throws Exception {
		return mainDAO.upcomingContents();
	};
	
}
