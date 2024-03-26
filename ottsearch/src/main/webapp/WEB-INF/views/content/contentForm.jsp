<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<c:set var="content" value="${content}"/>
<c:set var="formAction" value="${content != null ? '/content/update' : '/content/write'}"/>
<c:set var="contentId" value="${content != null ? content.contentId : ''}" />

<%
request.setCharacterEncoding("UTF-8");
%>

<head>
<meta charset="UTF-8">
	<title>OTT Search</title>
	<link rel="stylesheet" href="${contextPath}/resources/css/content/contentForm.css">
	<script src="${contextPath}/resources/js/content/contentForm.js" defer></script>
	<script>
		document.addEventListener('DOMContentLoaded', () => {
			checkTitle("<c:out value='${contentId}'/>");
		});
	</script>
</head>
<body>
<main id="main-content">
    <form action="<c:url value='${formAction}'/>" method="post" enctype="multipart/form-data">
      <h2>${ content != null ? '컨텐츠 수정' : '컨텐츠 작성' }</h2>
      <div class="content-info">
        <div class="title-box">
          <label for="title" class="form-label">제목</label>
          <input type="text" id="title" class="form-control" name="title" placeholder="제목을 입력하세요" autocomplete="off" value="${content != null ? content.title : ''}" required>
          <p id="title-message">이미 사용중인 제목입니다.</p>
        </div>
        <div class="poster">
          <label for="poster-img">포스터</label>
          <input type="file" class="custom-file-input" id="poster-img" name="image" onchange="setThumbnail(event)" required>
          <div class="image_container" >
            <c:if test="${content != null && content.imageUrl != null}">
                <script>
									document.addEventListener('DOMContentLoaded', () => {
										viewImage('/content/serveImage/${content.imageUrl}');
									});
                </script>
            </c:if>
           	<img id="thumbnail-image" src="<c:url value='/content/serveImage/${content.imageUrl}'/>" alt="컨텐츠 포스터">
          </div>
        </div>
        <div class="time-info">
          <div class="release-date">
            <label for="release_date" class="form-label">공개일</label>
				    <c:if test="${content != null && content.releaseDate != null}">
			        <fmt:formatDate value="${content.releaseDate}" pattern="yyyy-MM-dd" var="formattedDate"/>
			        <input type="date" id="release_date" class="form-control" name="releaseDate" value="${formattedDate}" required>
				    </c:if>
				    <c:if test="${content == null || content.releaseDate == null}">
			        <input type="date" id="release_date" name="releaseDate" required>
				    </c:if>
          </div>
          <div class="running-time">
            <label for="content-runtime" class="form-label">러닝타임</label>
            <input type="text" id="content-runtime" class="form-control" name="runningTime" placeholder="러닝타임을 입력하세요" value="${content != null ? content.runningTime : ''}" required>
          </div>
        </div>
      </div>
      <div class="genre">
        <h3>장르</h3>
	    	<div>
       		<c:forEach var="genre" items="${allGenres }">
		    		<c:if test="${genre.genreId > '0' && genre.genreId <= '5'}">
			  	  	<label>
			  	  		<input class="form-check-input" type="checkbox" value="${genre.genreId }" name="genreIds" ${content.genreIds.contains(genre.genreId) ? 'checked' : ''} required> ${genre.genreName }
			  	  	</label>
	    			</c:if>
    			</c:forEach>
	  	  </div>
	    	<div>
	    		<c:forEach var="genre" items="${allGenres }">
	 		    	<c:if test="${genre.genreId > '5' && genre.genreId <= '10'}">
				  	  <label>
				  	  	<input class="form-check-input" type="checkbox" value="${genre.genreId }" name="genreIds" ${content.genreIds.contains(genre.genreId) ? 'checked' : ''} required> ${genre.genreName }
				  	  </label>
			    	</c:if>
		    	</c:forEach>
	  	  </div>
	    	<div>
	    		<c:forEach var="genre" items="${allGenres }">
	 		    	<c:if test="${genre.genreId > '10' && genre.genreId <= '15'}">
				  	  <label>
				  	  	<input class="form-check-input" type="checkbox" value="${genre.genreId }" name="genreIds" ${content.genreIds.contains(genre.genreId) ? 'checked' : ''} required> ${genre.genreName }
				  	  </label>
			 		  </c:if>
       		</c:forEach>
	  	  </div>
      </div>
      <div class="platform">
        <h3>OTT 채널</h3>
        <div>
        	<c:forEach var="ott" items="${allOtts }">
	 		    	<c:if test="${ott.ottId > '0' && ott.ottId <= '3'}">
				  	  <label>
				  	  	<input class="form-check-input" type="checkbox" value="${ott.ottId}" name="ottIds" ${content.ottIds.contains(ott.ottId) ? 'checked' : ''} required> ${ott.ottName }
				  	  </label>
			 		  </c:if>
       		</c:forEach>
        </div>
        <div>
         	<c:forEach var="ott" items="${allOtts }">
	 		    	<c:if test="${ott.ottId > '3' && ott.ottId <= '6'}">
				  	  <label>
				  	  	<input class="form-check-input" type="checkbox" value="${ott.ottId}" name="ottIds" ${content.ottIds.contains(ott.ottId) ? 'checked' : ''} required> ${ott.ottName }
				  	  </label>
			 		  </c:if>
       		</c:forEach>
        </div>
      </div>
      <div class="age">
        <h3>등급</h3>
        <div>
          <label>
            <input class="form-check-input" type="radio" value="전체" name="rating" ${content != null && content.rating == '전체' ? 'checked' : '' }  required> 전체
          </label>
          <label>
            <input class="form-check-input" type="radio" value="12" name="rating" ${content != null && content.rating == '12' ? 'checked' : '' } required> 12
          </label>
          <label>
            <input class="form-check-input" type="radio" value="15" name="rating" ${content != null && content.rating == '15' ? 'checked' : '' } required> 15
          </label>
          <label>
            <input class="form-check-input" type="radio" value="청불"  name="rating" ${content != null && content.rating == '청불' ? 'checked' : '' } required> 청불
          </label>
        </div>
      </div>
      <div class="artist">
        <div>
          <label for="director">감독</label>
          <input type="text" id="director" class="form-control" name="director" placeholder="감독을 입력하세요" autocomplete="off" value="${content != null ? content.director : ''}" required>
        </div>
        <div class="actor-box">
          <label for="actor">출연배우</label>
            <div class="input-wrap">
              <input type="text" id="actor" class="actor-input" placeholder="출연 배우를 입력하세요" autocomplete="off" required>
              <button type="button" id="add-btn" class="btn btn-outline-light">추가</button>
            </div>
            <div class="actor-list">
            	<c:if test="${content != null}">
            		<c:forEach var="actorName" items="${content.actorNames}">
            			<script>
            				document.addEventListener('DOMContentLoaded', () => {
            					handleAddActor('${actorName}');
            				});
            			</script>
            		</c:forEach>
            	</c:if>
            </div>
        </div>
      </div>
      <div class="summary">
        <label for="description">줄거리</label>
        <textarea id="description" name="description" placeholder=" 줄거리를 입력하세요" required>${content != null ? content.description : '' }</textarea>
      </div>
 			<c:if test="${content != null}">
   			<input type="hidden" name="contentId" value="${content.contentId}">
			</c:if>
      <div class="btns">
        <a href="${contextPath}/content/contentList" class="btn btn-outline-light">뒤로가기</a>
        <button type="button" id="register-btn" class="btn btn-outline-light" onclick="validateForm()">${content != null ? '수정하기' : '작성하기'}</button>
      </div>
    </form>
  </main>
</body>
</html>
