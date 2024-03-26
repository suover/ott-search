<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
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
	<title>OTT Search</title>
	<script type="text/javascript" src="${contextPath}/resources/js/user/signupForm.js" defer></script>
	<link rel="stylesheet" href="${contextPath}/resources/css/user/signupForm.css">
</head>
<body>
<main id="primary-content">
<div class="main-box">
	<div class="signup-img">
		<h2>회원가입</h2>
		<img src="${contextPath}/resources/images/sign-up.png" alt="signup img">
	</div>
     <form action="/user/signup" method="post">
       <div id='email-wrap' class="input-box">
         <h4>이메일</h4>
         <div class="email-box">
           <input type="email" id="email" name="email" placeholder="이메일을 입력해주세요." autocomplete="off" required>
           <button type="button" id="email-dupl" class="btn btn-outline-light">중복확인</button>
         </div>
         <p class="email-ex">이메일 예시 : abcde123@gmail.com</p>
       </div>
       <div id='pwd-wrap' class="input-box">
         <h4>비밀번호</h4>
         <input type="password" id="password" name="password" placeholder="비밀번호를 입력해주세요." required>
         <p class="pw-ex">영문, 숫자, 특수문자(@, $, !, %, *, #, ?) 포함 8~20자</p>
       </div>
       <div id='nick-wrap' class="input-box">
         <h4>닉네임</h4>
         <div class="nickname-box">
	         <input type="text" id="nickname" name="nickname" placeholder="닉네임을 입력해주세요." autocomplete="off" required>
           <button type="button" id="nick-dupl" class="btn btn-outline-light">중복확인</button>
         </div>
         <p class="nickname-ex">2~12자 이내로 입력해주세요.</p>
       </div>
       <div class="input-box">
         <h4>이름</h4>
         <input type="text" id="username" name="userName" placeholder="이름을 입력해주세요." autocomplete="off" required>
         <p class="name-ex">한글 이름을 입력해주세요. (영문포함 X)</p>
       </div>
       <div id='tel-wrap' class="input-box">
        <h4>휴대전화</h4>
          <div class="phone-box">
            <input type="tel" id="phone" name="phoneNumber" placeholder="휴대전화번호를 입력해주세요." autocomplete="off" required>
            <button type="button" id="phone-dupl" class="btn btn-outline-light">중복확인</button>
          </div>
          <p class="tel-ex">000-0000-0000 형식으로 입력해주세요.</p>
       </div>
       <div class="input-box">
         <h4>생년월일</h4>
         <input type="date" id="birthday" name="birthDate" placeholder="생년월일을 입력해주세요." required>
         <p class="birth-ex"></p>
       </div>
       <div class="btns">
         <button type="reset" id="reset-btn" class="btn btn-light">취소</button>
         <button type="button" id="submit-btn" class="btn btn-light">가입</button>
       </div>
     </form>
</div>
</main>
</body>
</html>
