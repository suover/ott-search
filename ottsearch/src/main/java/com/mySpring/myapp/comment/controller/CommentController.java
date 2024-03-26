package com.mySpring.myapp.comment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mySpring.myapp.comment.service.CommentService;
import com.mySpring.myapp.comment.vo.CommentVO;

@RestController
@RequestMapping("/content")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 댓글 추가 (AJAX)
    @PostMapping("/{contentId}/comments")
    public ResponseEntity<?> addComment(@PathVariable("contentId") int contentId, 
                                        @RequestBody CommentVO comment) {
        try {
            comment.setContentId(contentId);
            commentService.addComment(comment);
            return ResponseEntity.ok().body("댓글이 추가되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("댓글 추가에 실패했습니다.");
        }
    }

    // 댓글 삭제 (AJAX)
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") int commentId) {
        try {
            commentService.deleteComment(commentId);
            return ResponseEntity.ok().body("댓글이 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("댓글 삭제에 실패했습니다.");
        }
    }

    // 댓글 수정 (AJAX)
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable("commentId") int commentId, 
                                           @RequestBody CommentVO comment) {
        try {
            comment.setCommentId(commentId);
            commentService.updateComment(comment);
            return ResponseEntity.ok().body("댓글이 수정되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("댓글 수정에 실패했습니다.");
        }
    }

    // 특정 컨텐츠의 댓글 목록 조회 (AJAX)
    @GetMapping("/{contentId}/comments")
    public ResponseEntity<?> getComments(@PathVariable("contentId") int contentId) {
        try {
            List<CommentVO> comments = commentService.getCommentsByContentId(contentId);        
            return ResponseEntity.ok().body(comments); // JSON 형식으로 응답
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("댓글 조회에 실패했습니다.");
        }
    }
}