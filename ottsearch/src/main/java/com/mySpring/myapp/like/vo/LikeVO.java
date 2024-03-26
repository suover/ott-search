package com.mySpring.myapp.like.vo;

public class LikeVO {
    private Long likeId;
    private Long contentId;
    private Long userId;

    // 기본 생성자
    public LikeVO() {}

    // 매개변수 있는 생성자
    public LikeVO(Long likeId, Long contentId, Long userId) {
        this.likeId = likeId;
        this.contentId = contentId;
        this.userId = userId;
    }

    // Getter 및 Setter 메소드
    public Long getLikeId() {
        return likeId;
    }

    public void setLikeId(Long likeId) {
        this.likeId = likeId;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // toString 메소드
    @Override
    public String toString() {
        return "LikeVO{" +
                "likeId=" + likeId +
                ", contentId=" + contentId +
                ", userId=" + userId +
                '}';
    }
}