<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"   isELIgnored="false"  %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<%
  request.setCharacterEncoding("UTF-8");
%>    

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>OTT Search</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@9/swiper-bundle.min.css"/>
  <script src="https://cdn.jsdelivr.net/npm/swiper@9/swiper-bundle.min.js"></script>
  <link rel="stylesheet" href="${contextPath}/resources/css/main/main.css">
 	<script type="text/javascript" src="${contextPath}/resources/js/main/main.js" defer></script>
</head>
<body>
<main id="main-content">
    <section class="main-banner">
      <div class="search-box">
        <p>작품을 검색해보세요!</p>
       		<form method="get" action="${contextPath }/mainSearchRequest">
       			<div class="search-input">
		          <div class="input-field">
		            <span class="fa fa-search search-icon"></span>
		            <input type="text" id="search" name="keyword">
		          </div>
	          	<button class="btn btn-outline-light search-btn">검색</button>
        		</div>
          </form>
      </div>
    </section>
    <section class="content-list">
    
      <div class="popular">
        <h2>인기작</h2>
        <div class="swiper popular-wrapper"> 
	        <div class="swiper-wrapper contents">
	          <c:forEach var="popularCont" items="${popularList}">
	          <div class="swiper-slide content">
	            <div class="imgbox">
	              <img src="<c:url value='/content/serveImage/${popularCont.imageUrl}'/>" alt="poster" >
	              <a href="${contextPath }/content/contentDetail/${popularCont.contentId}" class="detail-link"><span>자세히보기</span></a>
	            </div>
	            <div class="textbox">
	              <h3>${popularCont.title }</h3>
	              <span class="like"><span class="heart"></span>좋아요 ${popularCont.likes }</span>
	              <div class="otts">
									<c:forEach var="ott" items="${popularCont.ottNames}">
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
	          </div>
	          </c:forEach>
	        </div>
        </div>
        <button class="swiper-button-prev p-prev"></button>
        <button class="swiper-button-next p-next"></button>
      </div>
      
      <div class="upcoming">
        <h2>공개예정작</h2>
        <div class="swiper upcoming-wrapper">
	        <div class="swiper-wrapper contents">
	          <c:forEach var="upcomingCont" items="${upcomingList }">
		          <div class="swiper-slide content">
		            <div class="imgbox">
									<img src="<c:url value='/content/serveImage/${upcomingCont.imageUrl}'/>" alt="poster">
		              <a href="${contextPath }/content/contentDetail/${upcomingCont.contentId}" class="detail-link"><span>자세히보기</span></a>
		            </div>
		            <div class="textbox">
		              <h3>${upcomingCont.title }</h3>
		              <span class="date">공개일 : <fmt:formatDate value="${upcomingCont.releaseDate}" pattern="yyyy-MM-dd" /></span>
		              <div class="otts">
	                  <c:forEach var="ott" items="${upcomingCont.ottNames }">
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
		          </div>
	          </c:forEach>    
	        </div>
        </div>
        <button class="swiper-button-prev u-prev"></button>
        <button class="swiper-button-next u-next"></button>
      </div>
      
    </section>
  </main>
</body>
</html>