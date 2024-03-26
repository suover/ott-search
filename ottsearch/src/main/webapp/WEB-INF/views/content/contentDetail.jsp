<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<%
request.setCharacterEncoding("UTF-8");
%>

<head>
	<meta charset="UTF-8">
  <script type="text/javascript" src="${contextPath}/resources/js/content/contentDetail.js" defer></script>
	<link rel="stylesheet" href="${contextPath}/resources/css/content/contentDetail.css">
	<title>OTT Search</title>
	<script>
	    var isLogOn = ${sessionScope.isLogOn != null ? sessionScope.isLogOn : false};
	    var loggedInUserId = ${sessionScope.user.userId != null ? sessionScope.user.userId : false}; // 로그인한 사용자의 ID
	    var loggedInUserNickname = '${sessionScope.user.nickname}'; // 로그인한 사용자의 닉네임
		
		 $(document).ready(function() {
	 		// 댓글 목록 조회
		 	readComment('${content.contentId}');
			 
		 });
	</script>
</head>
<body>
 <main id="main-content">
    <section class="content">
      <div class="poster-img">
        <img src="<c:url value='/content/serveImage/${content.imageUrl}'/>" alt="${content.title } poster">
      </div>
      <div class="info">
        <div class="title-box">
          <h2>${content.title }</h2>
          <div class="feedback-btns">
            <button type="button" onclick="toggleLike(${content.contentId})" id="likeButton" class="btn btn-primary">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-hand-thumbs-up-fill thumbs" viewBox="0 0 16 16">
                <path d="M6.956 1.745C7.021.81 7.908.087 8.864.325l.261.066c.463.116.874.456 1.012.965.22.816.533 2.511.062 4.51a10 10 0 0 1 .443-.051c.713-.065 1.669-.072 2.516.21.518.173.994.681 1.2 1.273.184.532.16 1.162-.234 1.733q.086.18.138.363c.077.27.113.567.113.856s-.036.586-.113.856c-.039.135-.09.273-.16.404.169.387.107.819-.003 1.148a3.2 3.2 0 0 1-.488.901c.054.152.076.312.076.465 0 .305-.089.625-.253.912C13.1 15.522 12.437 16 11.5 16H8c-.605 0-1.07-.081-1.466-.218a4.8 4.8 0 0 1-.97-.484l-.048-.03c-.504-.307-.999-.609-2.068-.722C2.682 14.464 2 13.846 2 13V9c0-.85.685-1.432 1.357-1.615.849-.232 1.574-.787 2.132-1.41.56-.627.914-1.28 1.039-1.639.199-.575.356-1.539.428-2.59z"/>
              </svg>
              <span id="likeCount">${content.likes}</span>
            </button>
            <span class="reporting-date">작성일 <span><fmt:formatDate value="${content.createdDate}" pattern="yyyy-MM-dd HH:mm"/></span></span>
            <span class="hits">조회수 <span>${content.hits}</span></span>
          </div>
        </div>
        <div class="info-detail">
          <div>
            <h3>공개일</h3>
            <p><fmt:formatDate value="${content.releaseDate}" pattern="yyyy-MM-dd"/></p>
          </div>
          <div>
            <h3>OTT채널</h3>
            <div class="platform">
              <c:forEach var="ott" items="${content.ottNames}">
								<c:choose>
									<c:when test="${ott eq '넷플릭스' }">
										<span class="channel badge text-bg-danger">${ott}</span>
									</c:when>
									<c:when test="${ott eq '티빙' }">
										<span class="channel badge text-bg-warning">${ott}</span>
									</c:when>
									<c:when test="${ott eq '디즈니플러스' }">
										<span class="channel badge text-bg-primary">${ott}</span>
									</c:when>
									<c:when test="${ott eq '왓챠' }">
										<span class="channel badge text-bg-success">${ott}</span>
									</c:when>
									<c:when test="${ott eq '웨이브' }">
										<span class="channel badge text-bg-info">${ott}</span>
									</c:when>
									<c:when test="${ott eq '쿠팡플레이' }">
										<span class="channel badge text-bg-secondary">${ott}</span>
									</c:when>
								</c:choose>
							</c:forEach>
            </div>
          </div>
          <div>
            <h3>장르</h3>
            <p><c:forEach items="${content.genreNames}" var="genre">${genre} </c:forEach></p>
          </div>
          <div>
            <h3>러닝타임</h3>
            <p>${content.runningTime}분</p>
          </div>
          <div>
            <h3>이용연령</h3>
            <p>${content.rating}</p>
          </div>
          <div>
            <h3>감독</h3>
            <p>${content.director}</p>
          </div>
          <div>
            <h3>출연배우</h3>
            <p><c:forEach items="${content.actorNamesOutput}" var="actor">${actor} </c:forEach></p>
          </div>
          <div>
            <h3>줄거리</h3>
            <p class="summary">${content.description }</p>
          </div>
        </div>
      </div>
    </section>
      <section class="comments">
        <h2>댓글</h2>
        <div class="input-group mb-3 comment-input">
        	<textarea id="comment-text"></textarea>
			<button id="comment-btn" onclick="commentRight(isLogOn, '${contextPath}/loginForm', ${content.contentId})" class="btn btn-outline-light">댓글 추가</button>

        </div>
        <!-- 댓글 목록 -->
        <div id="comments">
            <!-- 댓글이 AJAX로 생성 -->
        </div>
      </section>  
	<div class="btns">
	    <a href="<c:url value='/content/contentList'/>" class="btn btn-outline-light">목록</a>
	    <c:if test="${sessionScope.user.userId == content.userId}">
	        <c:if test="${sessionScope.user.userRole eq 'admin'}">
	            <button type="button" id="delete-btn" class="btn btn-secondary" onclick="confirmDelete('${contentId}')">삭제</button>
	            <a href="<c:url value='/content/contentUpdate/${content.contentId}'/>" class="btn btn-primary">수정</a>
	        </c:if>
	    </c:if>
	    <c:if test="${sessionScope.user.userId != content.userId}">
	        <button type="button" class="btn btn-secondary" onclick="alert('본인의 글만 삭제 가능합니다.')">삭제</button>
	        <button type="button" class="btn btn-primary" onclick="alert('본인의 글만 수정 가능합니다.')">수정</button>
	    </c:if>
	</div>
  </main>
</body>
</html>