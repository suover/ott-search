package com.mySpring.myapp.content.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mySpring.myapp.content.service.ContentService;
import com.mySpring.myapp.content.vo.ActorVO;
import com.mySpring.myapp.content.vo.ContentVO;
import com.mySpring.myapp.content.vo.GenreVO;
import com.mySpring.myapp.content.vo.OttVO;
import com.mySpring.myapp.like.service.LikeService;
import com.mySpring.myapp.user.dto.UserDTO;

@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;
    
    @Autowired
    private LikeService likeService; // LikeService 주입
    
    private static final String UPLOAD_DIR = "C:\\Users\\minsu\\Desktop\\images"; // 파일 저장 경로 설정

    // 게시글 작성 페이지로 이동
    @RequestMapping(value = "/contentForm", method = RequestMethod.GET)
    public ModelAndView contentForm() throws Exception {
        ModelAndView mav = new ModelAndView("/content/contentForm");
        
        List<GenreVO> allGenres = contentService.getAllGenres(); // 모든 장르 목록을 가져옴
        List<OttVO> allOtts = contentService.getAllOtts(); // 모든 OTT 채널 목록을 가져옴

        mav.addObject("allGenres", allGenres); // 모델에 장르 목록 추가
        mav.addObject("allOtts", allOtts); // 모델에 OTT 채널 목록 추가
        return mav;
    }
    
    // 게시글 목록 페이지로 이동
    @RequestMapping(value = "/contentList", method = RequestMethod.GET)
    public ModelAndView contentList(
        @RequestParam(defaultValue = "1") int page, 
        @RequestParam(defaultValue = "20") int pageSize) throws Exception {
    	
        ModelAndView mav = new ModelAndView("/content/contentList");
        
        int totalContents = contentService.countContents(); // 전체 게시글 수
        int totalPages = (int) Math.ceil((double) totalContents / pageSize);

        int startRow = (page - 1) * pageSize + 1;
        int endRow = startRow + pageSize - 1;
        
        List<ContentVO> contentList = contentService.getAllContentsWithPaging(startRow, endRow); // 페이징 처리된 게시글 목록
        contentList = contentList.stream().filter(content -> content.getIsDeleted() != 'Y').collect(Collectors.toList()); // 삭제된 콘텐츠 제외
                        
        mav.addObject("contentList", contentList);
        mav.addObject("currentPage", page);
        mav.addObject("totalPages", totalPages);
        mav.addObject("pageSize", pageSize);
        mav.addObject("totalContents", totalContents);
        return mav;
    }

    // 게시글 작성 처리
    @PostMapping("/write")
    public ModelAndView write(ContentVO contentVO, @RequestParam("image") MultipartFile imageFile, HttpServletRequest request) throws Exception {
    	
        // 세션에서 로그인된 사용자 ID를 가져옴
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");
        contentVO.setUserId(user.getUserId()); // 로그인된 사용자의 ID 설정
    	
    	// 제목 검증
        if (!contentService.isTitleUnique(contentVO.getTitle())) {
            ModelAndView errorMav = new ModelAndView("/content/contentForm");
            errorMav.addObject("error", "이미 사용 중인 제목입니다.");
            errorMav.addObject("content", contentVO); // 사용자가 입력한 내용을 유지
            return errorMav;
        }
    	
    	contentVO.setCreatedDate(new Date()); // 현재 날짜 설정
    
    	   	
        if (!imageFile.isEmpty()) {
            String imageUrl = saveImage(imageFile); // 이미지 파일을 저장하고 URL을 반환하는 메소드
            contentVO.setImageUrl(imageUrl); // ContentVO에 이미지 URL 설정
        }
            	
        contentService.write(contentVO);
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/content/contentList");
        return mav;
    }
    
    @RequestMapping(value = "/checkTitle", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Boolean> checkTitleExists(@RequestParam("title") String title, 
                                                 @RequestParam(value = "contentId", required = false) Integer contentId) throws Exception {
        boolean isUnique;
        if (contentId == null) {
            // 새 게시글 작성 시
            isUnique = contentService.isTitleUnique(title);
        } else {
            // 게시글 수정 시
            isUnique = contentService.isTitleUnique(title, contentId);
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", !isUnique);
        return response;
    }
    
    private String saveImage(MultipartFile imageFile) {
        String filename = UUID.randomUUID().toString() + "-" + imageFile.getOriginalFilename();
        File targetFile = new File(UPLOAD_DIR + File.separator + filename);
        try {
            Files.copy(imageFile.getInputStream(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @RequestMapping("/serveImage/{filename:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) throws IOException {
        Path file = Paths.get(UPLOAD_DIR).resolve(filename);
        if (!Files.exists(file)) {
            return ResponseEntity.notFound().build();
        }
        Resource fileResource = new InputStreamResource(Files.newInputStream(file));
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(fileResource);
    }
    
    
    // 게시글 상세 페이지로 이동
    @RequestMapping(value = "/contentDetail/{contentId}", method = RequestMethod.GET)
    public ModelAndView contentDetail(@PathVariable("contentId") int contentId, HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("/content/contentDetail");
        contentService.updateContentHits(contentId); // 조회수 업데이트
        ContentVO content = contentService.getContentById(contentId); // 해당 게시글의 상세 정보를 가져오는 메서드

        HttpSession session = request.getSession(false); // 현재 세션 가져오기, 없으면 null 반환
        boolean userLiked = false;
        if (session != null && session.getAttribute("user") != null) {
            UserDTO user = (UserDTO) session.getAttribute("user");
            int userId = user.getUserId(); // 세션에서 로그인한 사용자의 ID를 가져옴
            userLiked = likeService.existsLike(contentId, userId); // 현재 사용자가 좋아요를 눌렀는지 확인
        }

        mav.addObject("content", content);
        mav.addObject("userLiked", userLiked); // 좋아요 상태 추가
        return mav;
    }
    
    // 게시글 수정 페이지로 이동
    @RequestMapping(value = "/contentUpdate/{contentId}", method = RequestMethod.GET)
    public ModelAndView contentUpdateForm(@PathVariable("contentId") int contentId) throws Exception {
        ModelAndView mav = new ModelAndView("/content/contentForm");
        ContentVO content = contentService.getContentById(contentId); // 해당 게시글의 데이터를 가져옴
        List<GenreVO> allGenres = contentService.getAllGenres(); // 모든 장르 목록을 가져옴
        List<OttVO> allOtts = contentService.getAllOtts(); // 모든 OTT 채널 목록을 가져옴
        List<ActorVO> allActors = contentService.getAllActors(); // 모든 배우 정보 가져옴
        
        // 장르 ID 로깅
        System.out.println("Associated Genres: " + content.getGenreIds());
        // OTT 채널 ID 로깅
        System.out.println("Associated OTT Channels: " + content.getOttIds());

        mav.addObject("content", content);
        mav.addObject("allGenres", allGenres); // 모델에 장르 목록 추가
        mav.addObject("allOtts", allOtts); // 모델에 OTT 채널 목록 추가
        mav.addObject("allActors", allActors); // 모델에 배우 목록 추가
        return mav;
    }

    
    // 게시글 수정 처리
    @PostMapping("/update")
    public ModelAndView updateContent(ContentVO contentVO, @RequestParam(value = "image", required = false) MultipartFile imageFile, HttpServletRequest request) throws Exception {
    	
        // 세션에서 로그인된 사용자 ID를 가져옴
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");
        contentVO.setUserId(user.getUserId()); // 로그인된 사용자의 ID 설정
    	
    	contentVO.setCreatedDate(new Date()); // 현재 날짜 설정
    	
        // is_deleted 설정
    	contentVO.setIsDeleted('N'); // 수정시 'N' 설정

    	
    	// 기존 이미지 유지
        if (imageFile == null || imageFile.isEmpty()) {
            ContentVO existingContent = contentService.getContentById(contentVO.getContentId());
            contentVO.setImageUrl(existingContent.getImageUrl());
        } else {
            // 새 이미지 처리
            String imageUrl = saveImage(imageFile);
            contentVO.setImageUrl(imageUrl);
        }

        contentService.updateContent(contentVO); // 게시글 수정 서비스 호출
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/content/contentDetail/" + contentVO.getContentId());
        return mav;
    }
    
    // 게시글 삭제 처리 (is_deleted를 'Y'로 설정)
    @PostMapping("/delete/{contentId}")
    public ModelAndView deleteContent(@PathVariable("contentId") int contentId) throws Exception {
        ContentVO content = contentService.getContentById(contentId);
        if (content != null) {
            content.setIsDeleted('Y'); // '삭제된' 상태로 설정
            contentService.updateContent(content); // 업데이트를 통해 is_deleted 상태 변경
        }
        return new ModelAndView("redirect:/content/contentList");
    }
    
    
}
