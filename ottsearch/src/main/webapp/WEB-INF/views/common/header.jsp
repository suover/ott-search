<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setCharacterEncoding("UTF-8");
%> 
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>OTT Search</title>
	<link rel="stylesheet" href="${contextPath}/resources/css/common/header.css">
</head>
<body>
	<header id="main-header">
		<div class="header-innerbox">
			<h1 class="logo"><a href="${contextPath}/ottSearch">OTT Search</a></h1>
			<nav>
				<a href="${contextPath}/content/contentList">게시판</a>
				<div class="log-btns">
				  <c:choose>
						<c:when test="${not empty sessionScope.user}">
							<a href="${contextPath}/user/logout" onclick="return confirm('로그아웃 하시겠습니까?');" class="btn btn-outline-light">로그아웃</a>
							<a href="${contextPath}/user/myPage" class="btn btn-warning">마이페이지</a>
                            <c:if test="${sessionScope.user.userRole == 'admin'}">
                                <a href="${contextPath}/user/adminPage" class="btn btn-success">관리자</a>
                            </c:if>
						</c:when>
						<c:otherwise>
							<a href="${contextPath}/loginForm" class="btn btn-outline-light">로그인</a>
							<a href="${contextPath}/signupForm" class="btn btn-warning">회원가입</a>
						</c:otherwise>
					</c:choose>
				</div>
			</nav>
		</div>
	</header>
</body>
</html>