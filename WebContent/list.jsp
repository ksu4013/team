<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<a href="insertForm.do">글쓰기</a>
	<table border="1">
		<tr>
			<td>글번호</td>
			<td>제목</td>
			<td>작성자</td>
			<td>작성일자</td>
			<td>조회수</td>
		</tr>
		<c:forEach var="board" items="${listModel.list }">
		<tr>
			<td>${board.seq }</td>
			<td><a href="/MVC/board/detail.do?seq=${board.seq }">${board.title }</a></td>
			<td>${board.writer }</td>
			<td>${board.regdate }</td>
			<td>${board.hitcount }</td>
		</tr>
		</c:forEach>
	</table>
	<br><br>
	
	<!-- 페이지처리 영역 -->
	<!-- 이전 -->
	<c:if test="${listModel.startPage > 5 }">
		<a href="list.do?pageNum=${listModel.startPage -1 }">[이전]</a>
	</c:if>
	
	<!-- 페이지 목록 -->
	<c:forEach var="pageNo" begin="${listModel.startPage }"
						end="${listModel.endPage }">
		<c:if test="${listModel.requestPage == pageNo }"><b></c:if>				
			<a href="list.do?pageNum=${pageNo }">[${pageNo }]</a>
		<c:if test="${listModel.requestPage == pageNo }"></b></c:if>					
	</c:forEach>
	
	<!-- 이후 -->
	<c:if test="${listModel.endPage < listModel.totalPageCount }">
		<a href="list.do?pageNum=${listModel.endPage +1 }">[이후]</a>
	</c:if>
	
	
	<form action="list.do" method="post">
		<input type="checkbox" name="area" value="title">제목
		<input type="checkbox" name="area" value="writer">작성자
		<input type="text" name="searchKey" size="10"/>
		<input type="submit" value="검색">
	</form>
	
	
</body>
</html>














