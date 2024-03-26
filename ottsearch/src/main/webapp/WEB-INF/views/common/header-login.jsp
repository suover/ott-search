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
	<link rel="stylesheet" href="${contextPath}/resources/css/common/header-login.css">
</head>
<body>
  <header id="main-header">
    <div class="header-innerbox">
      <h1 class="logo"><a href="${contextPath}/ottSearch">OTT Search</a></h1>
      <nav class="gnb">
        <ul>
          <li><a href="${contextPath}/content/contentList">게시판</a></li>
        </ul>
      </nav>
    </div>
  </header>
</body>
</html>