<%@ page language="java" contentType="text/html; utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Solita Dev Academy 2021</title>
<link rel="stylesheet" href="/styles/style.css">
</head>
<body>
<button onClick="listNames()">Alphabetically</button>
	<div id="content">
		<table>
			<thead>
				<tr>
					<th>Name</th>
					<th>Amount</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${names }" var="name">
					<tr>
						<td><c:out value="${name }" /></td>
						<td><c:out value="${name.getAmount() }" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<script src="/js/editContent.js"></script>
</body>
</html>
