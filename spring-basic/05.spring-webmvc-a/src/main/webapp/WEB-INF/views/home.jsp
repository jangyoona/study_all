<%@page import="java.util.Date"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=utf-8" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
	<br /><br /><br />
	<div style="padding-left:20px">
		<h2>Spring MVC Controller Demo</h2>
		
		<h3>1. <a href="demo/param?data1=this%20is%20test%20data&data2=100">Process Request Parameter (GET)</a></h3>
		<h3>2. Process Request Parameter (POST)</h3>
		<form action="demo/param" method="post">
			이름 : <input type="text" name="name" value="홍길동" /><br />
			전화 : <input type="text" name="phone" /><br />
			메일 : <input type="text" name="email" /><br />
			나이 : <input type="text" name="age" /><br />
			생일 : <input type="date" name="birthDate" /><br />
			<input type="submit" value="전송" />
		</form>
		
		<h3>9. Send Multiple Objects</h3>
		<form id="send-list-form" action="demo/send-list" method="post">
			이름1 : <input type="text" name="persons[0].name" value="홍길동" /><br />
			전화1 : <input type="text" name="persons[0].phone" /><br />
			메일1 : <input type="text" name="persons[0].email" /><br />
			나이1 : <input type="text" name="persons[0].age" /><br />
			<hr>
			이름2 : <input type="text" name="persons[1].name" value="홍길동" /><br />
			전화2 : <input type="text" name="persons[1].phone" /><br />
			메일2 : <input type="text" name="persons[1].email" /><br />
			나이2 : <input type="text" name="persons[1].age" /><br />
			<input type="submit" value="전송" id="send-list"/>
		</form>
		
		<h3>3. <a href="#">Transfer Data from Controller to View (Refer to 1, 2)</a></h3>
		<h3>4. <a href="demo/redirect">Redirect to Another Action</a></h3>
		<h3>5. <a href="demo/forward">Forward to HTML</a></h3>
		<h3>6-1. <a href="hello.html">static resource not in resources</a></h3>
		<h3>6-2. <a href="resources/hello.html">static resource in resources</a></h3>
		<h3>7. <a href="demo/custom-view">Custom View</a></h3>
		<h3>8-1. <a href="demo/sync">Synchronous Request/Response</a></h3>
		<h3>8-2. <a href="javascript:" class="async-link" data-no="1">Asynchronous Request/Response 1</a></h3>
		<h3>8-3. <a href="javascript:" class="async-link" data-no="2">Asynchronous Request/Response 2</a></h3>
		<h3>8-4. <a href="javascript:" id="async-link3">Asynchronous Request/Response 3</a></h3>
		<div style="border:solid 1px; margin-bottom: 2px; padding: 5px"><%= new Date() %></div>
		<div id="message" style="border:solid 1px; margin-bottom: 2px; padding: 5px">${ syncResult }</div>
	</div>
	<br><br><br><br><br>
</body>

	<script src="http://code.jquery.com/jquery-3.7.1.js" ></script>
	<script type="text/javascript">
		$(function() {
			$('.async-link').on("click", (e) => {
				$.ajax({
					"url": "demo/async" + $(e.target).data('no'),
					"method" : "GET",
					"data" : { "a" : "123", "b" : "456"}, // "a=123&b=456" 이렇게도 됨.
					"dataType" : "json",
					"success" : function(data, status, xhr) {
						const jsonString = JSON.stringify(data); // JSON -> 문자열로 변환
						html = "<h3>PERSON INFO</h3>" +
						   "<table>" +
						   "<tr><th>이름</th><td>" + data.name + "</td></tr>" +
						   "<tr><th>이메일</th><td>" + data.email + "</td></tr>" +
						   "<tr><th>전화</th><td>" + data.phone + "</td></tr>" +
						   "<tr><th>나이</th><td>" + data.age + "</td></tr>" +
						   "<tr><th>생일</th><td>" + data.birthDate + "</td></tr>" +
						   "</table>"
						$('#message').html(html);
					},
					"error" : function(xhr, status, err) {
						$('#message').html("요청 실행 중 오류 발생");
						
					}
					
				});
				
			});
			// //////////////////////////////////////////
			
			$('#async-link3').on("click", (e) => {
				$('#message').load("demo/async3");
				
				
			});
	
		});
	
	
	</script>
</html>
