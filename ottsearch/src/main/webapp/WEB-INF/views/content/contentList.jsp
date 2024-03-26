<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<%
  request.setCharacterEncoding("UTF-8");
%>  
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <script type="text/javascript" src="${contextPath}/resources/js/content/contentList.js" defer></script>
  <link rel="stylesheet" href="${contextPath}/resources/css/content/contentList.css">
  <title>OTT Search</title>
</head>
<body>
<main>
<div class="card_board py-5">
	<c:choose>
		<c:when test="${empty contentList}">
			<div class="no-content">
				<p>등록된 컨텐츠가 없습니다.</p>
			</div>
		</c:when>
		<c:when test="${not empty contentList}">
			<div id="main-container" class="container">
				<div class="d-flex justify-content-end mt-3 mb-5 px-5">
				<form role="search" action="${contextPath }/content/searchContents" method="get" class="mb-3 d-flex">
					<select name="searchType" class="search-select form-select form-select-dark text-bg-dark me-2" style="width:auto;">
						<option value="1">제목</option>
						<option value="2">장르</option>
						<option value="3">채널</option>
					</select>
					<input name="keyword" type="search" class="form-control form-control-dark text-bg-dark" aria-label="Search" style="width:auto;">
					<button type="submit" class="btn btn-dark ms-2 btn-outline-light">검색</button>
				</form>
				</div>
				<!-- 카드출력 -->		
				<div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 row-cols-xl-5 g-4 px-5 mt-1">
					<c:forEach var="content" items="${contentList }">
						<a href="${contextPath}/content/contentDetail/${content.contentId}">
							<div class="col">
								<div class="card bg-transparent">
									<img src="<c:url value='/content/serveImage/${content.imageUrl}'/>" alt="${content.title}" class="card-img-top" width="200px" height="300px" />
									<div class="card-body bg-light">
										<div class="d-flex justify-content-between" style="flex-wrap: nowrap;">
											<div style="flex-grow: 1; max-width: 80%;">
												<p class="card-title fw-bold">${content.title}</p>
											</div>
											<c:choose>
												<c:when test="${content.rating eq '청불' }">
													<span class="badge text-bg-danger position-absolute end-0 me-2 mt-1">${content.rating}</span>
												</c:when>
												<c:when test="${content.rating eq '15' }">
													<span class="badge text-bg-warning position-absolute end-0 me-2 mt-1">${content.rating}</span>
												</c:when>
												<c:when test="${content.rating eq '12' }">
													<span class="badge text-bg-success position-absolute end-0 me-2 mt-1">${content.rating}</span>
												</c:when>
												<c:when test="${content.rating eq '청불' }">
													<span class="badge text-bg-primary position-absolute end-0 me-2 mt-1">${content.rating}</span>
												</c:when>
											</c:choose>
										</div>
										<p class="running-time card-text">${content.runningTime}분</p>
										<div class="genreList">
											<c:forEach var="genre" items="${content.genreNames}">
												<span class="card-text">${genre}</span>
											</c:forEach>
										</div>
										<div class="channels mt-2">
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
										<p class="card-text">공개일 <span class="release-date"><fmt:formatDate value="${content.releaseDate}" pattern="yyyy-MM-dd"/></span></p>
										<div class="d-flex justify-content-between align-items-center">
										  <div>
			                  <p>댓글 <span>${content.commentCount}</span></p>
			                </div>
											<div class="likes">
												<img class="d-inline" src="${contextPath}/resources/images/like.png" width="12px">
												<span>${content.likes}</span>
											</div>
										</div>
									</div>
								</div>
							</div>
						</a>
					</c:forEach>
				</div>
			</div>
	        <nav aria-label="Page navigation example" id="pagenate">
	            <ul class="pagination justify-content-center mb-5 custom-pagination">
	                <!-- 이전 페이지 -->
	                <c:if test="${currentPage > 1}">
	                    <li class="page-item">
                    		<c:choose>
                    			<c:when test="${!(searchType eq '') && !(keyword eq '')}">
                    				<a class="page-link" href="?searchType=${searchType}&keyword=${keyword }&page=${currentPage - 1}&pageSize=${pageSize}">이전</a>
                    			</c:when>
                    			<c:otherwise>
		                        <a class="page-link" href="?page=${currentPage - 1}&pageSize=${pageSize}">이전</a>
                    			</c:otherwise>
                    		</c:choose>
	                    </li>
	                </c:if>
	                
	                <!-- 페이지 번호 -->
	                <c:forEach begin="1" end="${totalContents / pageSize + (totalContents % pageSize == 0 ? 0 : 1)}" var="pageNum">
	                    <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                    		<c:choose>
                    			<c:when test="${!(searchType eq '') && !(keyword eq '')}">
                    				<a class="page-link" href="?searchType=${searchType}&keyword=${keyword}&page=${pageNum}&pageSize=${pageSize}">${pageNum}</a>
                    			</c:when>
                    			<c:otherwise>
                    				<a class="page-link" href="?page=${pageNum}&pageSize=${pageSize}">${pageNum}</a>	
                    			</c:otherwise>
                    		</c:choose>
	                    </li>
	                </c:forEach>
	
	                <!-- 다음 페이지 -->
	                <c:if test="${currentPage < totalContents / pageSize}">
	                    <li class="page-item">
                    		<c:choose>
                    			<c:when test="${!(searchType eq '') && !(keyword eq '')}">
                    				<a class="page-link" href="?searchType=${searchType}&keyword=${keyword}&page=${currentPage + 1}&pageSize=${pageSize}">다음</a>
                    			</c:when>
                    			<c:otherwise>
		                        <a class="page-link" href="?page=${currentPage + 1}&pageSize=${pageSize}">다음</a>
                    			</c:otherwise>
                    		</c:choose>
	                    </li>
	                </c:if>
	            </ul>
        	</nav>
		</c:when>
	</c:choose>
    <div class="container">
        <div class="d-flex justify-content-center px-5 mt-5">
            <% if (session.getAttribute("userRole") != null && session.getAttribute("userRole").equals("admin")) { %>
                <a href="${contextPath }/content/contentForm" class="btn btn-dark btn-outline-light">글작성</a>
            <% } %>
        </div>
    </div>
</div>
</main>
</body>
</html>