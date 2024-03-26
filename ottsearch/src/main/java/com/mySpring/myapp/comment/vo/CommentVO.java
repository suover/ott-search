package com.mySpring.myapp.comment.vo;

import java.util.Date;

public class CommentVO {
    private int commentId;      // 댓글 식별자
    private int contentId;      // 컨텐츠 식별자
    private int userId;         // 사용자 식별자
    private String commentText; // 댓글 내용
    private Date commentDate;   // 댓글 작성일
    private char isDeleted;     // 삭제 여부 ('Y' 또는 'N')
    private String nickname;    // 사용자 닉네임

    // 기본 생성자
    public CommentVO() {}

    // 매개변수 있는 생성자
    public CommentVO(int commentId, int contentId, int userId, String commentText, Date commentDate, char isDeleted, String nickname) {
        this.commentId = commentId;
        this.contentId = contentId;
        this.userId = userId;
        this.commentText = commentText;
        this.commentDate = commentDate;
        this.isDeleted = isDeleted;
        this.nickname = nickname;
    }

    // Getter 및 Setter 메소드
    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public char getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(char isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    // toString 메소드
    @Override
    public String toString() {
        return "CommentVO [commentId=" + commentId + ", contentId=" + contentId + ", userId=" + userId
                + ", commentText=" + commentText + ", commentDate=" + commentDate + ", isDeleted=" + isDeleted
                + ", nickname=" + nickname + "]";
    }
}