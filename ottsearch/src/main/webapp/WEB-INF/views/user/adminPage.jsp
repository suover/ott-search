<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    isELIgnored="false"  %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
  request.setCharacterEncoding("UTF-8");
%>    
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
	<script type="text/javascript" src="${contextPath}/resources/js/user/adminPage.js" defer></script>
	<link rel="stylesheet" href="${contextPath}/resources/css/user/adminPage.css">
	<title>OTT Search</title>
</head>
<body>
  <section class="list-wrapper">
    <h2>OTT Search 회원리스트</h2>
    <form action='#' method="post">
	    <table class="user-list">
	      <caption>회원 리스트</caption>
	      <tr>
	        <th>회원번호</th>
	        <th>이메일</th>
	        <th>비밀번호</th>
	        <th>이름</th>
	        <th>닉네임</th>
	        <th>전화번호</th>
	        <th>생일</th>
	        <th>가입일</th>
	        <th>권한</th>
	        <th>탈퇴여부</th>
	      </tr>
	       <c:forEach var="user" items="${userList }">
		      <tr>
		        <td>${user.userId}</td>
		        <td>${user.email}</td>
		        <td>${user.password}</td>
		        <td>${user.userName}</td>
		        <td>${user.nickname}</td>
		        <td>${user.phoneNumber }</td>
		        <td><fmt:formatDate value="${user.birthDate}" pattern="yyyy-MM-dd"/></td>
		        <td><fmt:formatDate value="${user.signupDate}" pattern="yyyy-MM-dd"/></td>
		        <td class="admin-td">
		          <select class="admin-select" data-user-id="${user.userId}">
				    <option value="admin" ${user.userRole == 'admin' ? 'selected' : ''}>admin</option>
				    <option value="user"  ${user.userRole == 'user' ? 'selected' : ''}>user</option>
					</select>
		        </td>
		        <td>${user.isDeleted}</td>
		      </tr>
	      </c:forEach>
	    </table>
    </form>
    <nav aria-label="Page navigation example" id="pagenate">
      <ul class="pagination justify-content-center mb-5 custom-pagination">
        <!-- 이전 페이지 -->
        <c:if test="${currentPage > 1}">
            <li class="page-item">
                <a class="page-link" href="?page=${currentPage - 1}&pageSize=${pageSize}">이전</a>
            </li>
        </c:if>
        
        <!-- 페이지 번호 -->
        <c:forEach begin="1" end="${totalContents / pageSize + (totalContents % pageSize == 0 ? 0 : 1)}" var="pageNum">
            <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                <a class="page-link" href="?page=${pageNum}&pageSize=${pageSize}">${pageNum}</a>
            </li>
        </c:forEach>

        <!-- 다음 페이지 -->
        <c:if test="${currentPage < totalContents / pageSize}">
            <li class="page-item">
                <a class="page-link" href="?page=${currentPage + 1}&pageSize=${pageSize}">다음</a>
            </li>
        </c:if>
      </ul>
   </nav>
    <div class="container">
      <div class="d-flex justify-content-center px-5 mt-5">
	    	<button type="button" onclick="updateAllRoles()" class="btn btn-dark btn-outline-light">수정</button>
      </div>
  	</div>
  </section>
</body>
</html>
