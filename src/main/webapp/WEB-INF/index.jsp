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
	<input type="text" id="hiddenInput" autocomplete="false" autofocus />
	<div id="content" class="console">
		<div id="output"></div>
		<div id="input">
			<span id="inputSign"></span><span id="inputField"></span><span
				id="inputCursor"></span>
		</div>
	</div>
	<script src="/js/console.js"></script>
</body>
</html>
