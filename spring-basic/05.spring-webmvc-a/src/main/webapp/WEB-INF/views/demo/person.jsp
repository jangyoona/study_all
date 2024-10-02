<%@ page language="java"
		 contentType="text/html; charset=UTF-8"
   		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>PERSON INFO (made in jsp)</h3>
	<table>
		<tr>
			<td>이름</td>
			<td>${ person.name }</td>
		</tr>
		<tr>
			<td>이메일</td>
			<td>${ person.email }</td>
		</tr>
		<tr>
			<td>전화</td>
			<td>${ person.phone }</td>
		</tr>
		<tr>
			<td>나이</td>
			<td>${ person.age }</td>
		</tr>
		<tr>
			<td>생년월일</td>
			<td>${ person.birthDate }</td>
		</tr>
	</table>
</body>
</html>