package com.mySpring.myapp.main.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mySpring.myapp.main.service.MainService;
import com.mySpring.myapp.content.vo.ContentVO;

@Controller
public class MainController {
	
	@Autowired
	private MainService mainService;
	
	@RequestMapping(value = { "/", "/ottSearch"}, method = RequestMethod.GET)
	private ModelAndView main(
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword
			) throws Exception {
		
        ModelAndView mav = new ModelAndView();
        
        List<ContentVO> popularContentList = mainService.popularContents();
        List<ContentVO> upcomingContentList = mainService.upcomingContents();
        mav.addObject("popularList", popularContentList);
        mav.addObject("upcomingList", upcomingContentList);
        
        return mav;
	}
	
	@RequestMapping(value = { "/mainSearchRequest"}, method = RequestMethod.GET)
	private ModelAndView searchInMain(
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword
			) throws Exception {
		
        ModelAndView mav = new ModelAndView();
        mav.addObject("searchType", "1");
        mav.addObject("keyword", keyword);
        mav.setViewName("redirect:/content/searchContents/");
		return mav;
	}

}
