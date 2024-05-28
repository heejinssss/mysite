<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="<%=request.getContextPath() %>/assets/css/main.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp" /> <%-- JSP 지시어 Tag (Not HTML Tag --%>
		<div id="wrapper">
			<div id="content">
				<div id="site-introduction">
					<img id="profile" src="<%=request.getContextPath() %>/assets/images/naruto.png" style='width: 250px'>
					<h2>안녕하세요. 배희진의  mysite에 오신 것을 환영합니다.</h2>
					<p>
						블로그 실습<br><br>
						<a href="#">방명록</a>에 글 남기기<br>
					</p>
				</div>
			</div>
		</div>
		<jsp:include page ="/WEB-INF/views/includes/navigation.jsp" />
		<jsp:include page ="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>