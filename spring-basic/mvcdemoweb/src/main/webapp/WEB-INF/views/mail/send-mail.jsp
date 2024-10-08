<%@page import="com.demoweb.dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>


<!DOCTYPE html>

<html>
<head>
	<meta charset="utf-8" />
	<title>게시판글쓰기</title>
	<link rel="Stylesheet" href="/mvcdemoweb/styles/default.css" />
	<link rel="Stylesheet" href="/mvcdemoweb/styles/input2.css" />
</head>
<body>

	<div id="pageContainer">
	
		<jsp:include page="/WEB-INF/views/modules/header.jsp" />
		
		<div style="padding-top:25px;text-align:center">
		<div id="inputcontent">
		    <div id="inputmain">
		        <div class="inputsubtitle">메일 보내기</div>
		        <form action="send-mail" method="post">
		        <table>
		            <tr>
		                <th>제목</th>
		                <td>
		                    <input type="text" name="title" style="width:580px" />
		                </td>
		            </tr>
		            <tr>
		                <th>작성자</th>
		                <td>
		                <% MemberDto member = (MemberDto)session.getAttribute("loginuser"); %>
		                <input type="hidden" name="from" value="${ loginuser.email }"/>
		                ${ sessionScope.loginuser.memberid }
		                </td>
		            </tr>
		            <tr>
		                <th>받는 사람</th>
		                <td>
		                    <input type="text" name="to" style="width:580px;height:20px" />
		                </td>
		            </tr>
		            <tr>
		                <th>글내용</th>
		                <td>
		                	<textarea name="content" style="width:580px" rows="15"></textarea>
		                </td>
		            </tr>
		        </table>
		        <div class="buttons">
		        	<input type="submit" value="글쓰기" style="height:25px" />
		        	<input type="button" id="cancel-btn" value="취소" style="height:25px"  />
		        </div>
		        </form>
		    </div>
		</div>   	
	
	</div>
	</div>
	
<script src="http://code.jquery.com/jquery-3.7.1.js"></script>
<script type="text/javascript">
	$(function() {
		$('#cancel-btn').on("click", function(event) {
			location.href = "/mvcdemoweb/board/list";
			// history.back(); // 이전으로 이동하기 이것도 괜춘
		});
		
	});
	</script>
</body>
</html>