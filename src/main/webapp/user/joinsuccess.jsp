<%@page import="data.dao.UserDao"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link href="https://fonts.googleapis.com/css2?family=Dokdo&family=Gaegu&family=Gugi&family=Nanum+Pen+Script&display=swap" rel="stylesheet">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.5.0.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<title>Insert title here</title>
<style type="text/css">
.container,
.button  {
	display: flex;
	justify-content: center;
	align-item: center;	
}


</style>
</head>
<body>
<%
//id 읽음
String id=request.getParameter("id");

//dao 선언
UserDao dao=new UserDao();

//아이디에 대한 이름 가져오기
String name=dao.getName(id);
%>

<div style="width:100%" class="container">
		<b><%=name %>님의 회원가입을 축하합니다.</b>
		<br><br>
</div>
<div class="button">
		<button type="button" class="btn btn-info" onclick="location.href='index.jsp?main=login/loginform.jsp'">로그인</button>
		&nbsp
		<button type="button" class="btn btn-default" onclick="location.href='index.jsp'">Home</button>
</div>
</body>
</html>

