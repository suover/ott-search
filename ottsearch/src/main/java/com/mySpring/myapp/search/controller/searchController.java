package com.mySpring.myapp.search.controller;

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

import com.mySpring.myapp.search.sevice.SearchService;
import com.mySpring.myapp.content.vo.ContentVO;

@Controller
@RequestMapping("/content")
public class searchController {
	@Autowired
	private SearchService searchService;
	
    // 페이징 처리
    @RequestMapping(value = "/searchContents", method = RequestMethod.GET)
    public ModelAndView searchResultPaging(
    		@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
    		@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam(defaultValue = "1") int page, 
			@RequestParam(defaultValue = "20") int pageSize
    		) throws Exception {
    	
    	int totalCountContents = searchService.countSearchList(searchType, keyword); // 검색된 게시글 총 갯수
    	int totalPageNum = (int)Math.ceil((double)totalCountContents/pageSize); // 페이징 번호 갯수 -> 총 갯수 / 한 페이지에 보일 컨텐츠 갯수
    	
    	int startRow = (page - 1) * pageSize + 1;
    	int endRow = startRow + pageSize - 1;
    	      
    	ModelAndView mav = new ModelAndView();
    	List<ContentVO> contentList = searchService.searchListPaging(searchType, keyword, startRow, endRow);
    	mav.addObject("contentList", contentList);
		mav.addObject("currentPage", page);
		mav.addObject("totalPages", totalPageNum);
		mav.addObject("pageSize", pageSize);
		mav.addObject("totalContents", totalCountContents);
		mav.addObject("searchType", searchType);
		mav.addObject("keyword", keyword);
    	return mav;
    }
    
}
