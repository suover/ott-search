package com.mySpring.myapp.like.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mySpring.myapp.like.service.LikeService;

@Controller
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    // AJAX를 사용한 컨텐츠 좋아요 토글
    @PostMapping("/toggle/{contentId}")
    @ResponseBody
    public Map<String, Object> toggleLike(@PathVariable("contentId") int contentId,
                                          @RequestParam("userId") int userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (likeService.existsLike(contentId, userId)) {
                likeService.removeLike(contentId, userId);
                response.put("liked", false);
            } else {
                likeService.addLike(contentId, userId);
                response.put("liked", true);
            }
            response.put("likeCount", likeService.getLikeCount(contentId));
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", "오류가 발생했습니다.");
        }
        return response;
    }

    // 특정 컨텐츠의 좋아요 수 확인
    @GetMapping("/count/{contentId}")
    @ResponseBody
    public int getLikeCount(@PathVariable("contentId") int contentId) {
        try {
            return likeService.getLikeCount(contentId);
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // 에러 시 -1 반환
        }
    }
}