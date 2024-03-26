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
	<script type="text/javascript" src="${contextPath}/resources/js/user/loginForm.js" defer></script>
	<link rel="stylesheet" href="${contextPath}/resources/css/user/loginForm.css">
	<title>OTT Search</title>
	<c:choose>
		<c:when test="${result=='loginFailed' }">
		  <script>
		    window.onload=function(){
		      alert("아이디나 비밀번호가 틀립니다. 다시 로그인 해주세요!");
		    }
		  </script>
		</c:when>
	</c:choose>  
</head>

<body>
<main id="main-content">
    <div class="form-box">
      <div class="login-img">Welcome!</div>
      
      <!-- controller 작성 후 action 연결하기 -->
      <form id="login-form" action="${contextPath}/login" method="post">
        <div class="input-field">
          <h4>이메일</h4>
          <input type="email" id="email" name="email" placeholder="Email address" required><span>
          <p class="confirm hide">이메일 주소를 입력해주세요!</p>
        </div>
        <div class="input-field">
          <h4>비밀번호</h4>
          <input type="password" id="pw" name="password" placeholder="Password" required>
          <p class="confirm hide">비밀번호를 입력해주세요!</p>
        </div>
      </form>
      <div class="btns">
        <a href="${contextPath }/signupForm" class="btn btn-light">회원가입</a>
        <button id="submit-btn" type="button" class="btn btn-light">로그인</button>
      </div>
    </div>
  </main>
</body>
</html>
