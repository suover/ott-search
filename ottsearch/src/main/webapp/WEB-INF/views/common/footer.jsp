<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"   isELIgnored="false"
 %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setCharacterEncoding("UTF-8");
%> 
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="${contextPath}/resources/css/common/footer.css">
</head>
<body>
	<footer id="main-footer">
		<div class="footer-innerbox">
			<h2><a href="${contextPath}/ottSearch">OTT Search</a></h2>
			<div class="content-box">
				<p>모든 OTT의 오리지널 컨텐츠를 검색할 수 있는 사이트로<br>
				  개봉예정일, 카테고리별 정보를 제공합니다.
				</p>
				<ul class="ott-list">
					<li><a href="#">Netflix</a></li>
					<li><a href="#">Tving</a></li>
					<li><a href="#">Disney Plus</a></li>
					<li><a href="#">Coupang Play</a></li>
					<li><a href="#">Watcha</a></li>
					<li><a href="#">Wavve</a></li>
				</ul>
			</div>
		</div>
	</footer>
</body>
</html>