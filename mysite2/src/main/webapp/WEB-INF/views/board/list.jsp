<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form"
					action="${pageContext.request.contextPath}/board" method="get">
					<input type="hidden" name="a" value="board" /> <input type="text"
						id="kwd" name="kwd" value="${kwd != null ? kwd : ''}"> <input
						type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:set var="count" value="${totalPosts }" />
					<c:forEach items="${list }" var="vo" varStatus="status">
						<tr>
							<td>${count}</td>
							<c:set var="count" value="${count - 1 }" />
							<c:choose>
								<c:when test="${vo.depth eq 1 }">
									<td style="text-align:left; padding-left:${vo.depth * 20 }px">
										<c:if test="${not empty kwd }">
											<a
												href="${pageContext.request.contextPath}/board?a=view&no=${vo.no }&page=${currentPage }&kwd=${kwd }">${vo.title }</a>
										</c:if> <c:if test="${empty kwd }">
											<a
												href="${pageContext.request.contextPath}/board?a=view&no=${vo.no }&page=${currentPage }">${vo.title }</a>
										</c:if>
									</td>
								</c:when>
								<c:otherwise>
									<td style="text-align:left; padding-left:${vo.depth * 20 }px">
										<img
										src='${pageContext.request.contextPath }/assets/images/reply.png' />
										<c:if test="${not empty kwd}">
											<a
												href="${pageContext.request.contextPath }/board?a=view&no=${vo.no }&page=${currentPage }&kwd=${kwd }">${vo.title }</a>
										</c:if> <c:if test="${empty kwd}">
											<a
												href="${pageContext.request.contextPath }/board?a=view&no=${vo.no }&page=${currentPage }">${vo.title }</a>
										</c:if>
									</td>
								</c:otherwise>
							</c:choose>
							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<c:choose>
								<c:when test="${authUser.no eq vo.userNo }">
									<td><c:if test="${not empty kwd }">
											<a
												href="${pageContext.request.contextPath }/board?a=delete&no=${vo.no }&page=${currentPage }&kwd=${kwd }"
												class="del"
												onclick="return showAlert('${pageContext.request.contextPath }/board?a=delete&no=${vo.no }')">삭제</a>
										</c:if> <c:if test="${empty kwd }">
											<a
												href="${pageContext.request.contextPath }/board?a=delete&no=${vo.no }&page=${currentPage }"
												class="del"
												onclick="return showAlert('${pageContext.request.contextPath }/board?a=delete&no=${vo.no }')">삭제</a>
										</c:if></td>
								</c:when>
								<c:otherwise>
									<td></td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
				</table>
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:if test="${not empty kwd }">
							<li><a
								href="${pageContext.request.contextPath}/board?a=board&page=${currentPage > 1? currentPage - 1 : 1 }&kwd=${kwd }">◀</a></li>
						</c:if>
						<c:if test="${empty kwd }">
							<li><a
								href="${pageContext.request.contextPath}/board?a=board&page=${currentPage > 1? currentPage - 1 : 1 }">◀</a></li>
						</c:if>
						<c:forEach var="i" begin="${startPage }" end="${endPage }">
							<c:choose>
								<c:when test="${i eq currentPage }">
									<li class="selected">${i}</li>
								</c:when>
								<c:otherwise>
									<c:if test="${not empty kwd }">
										<li><a
											href="${pageContext.request.contextPath }/board?a=board&page=${i }&kwd=${kwd }">${i }</a></li>
									</c:if>
									<c:if test="${empty kwd }">
										<li><a
											href="${pageContext.request.contextPath }/board?a=board&page=${i }">${i }</a></li>
									</c:if>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${not empty kwd }">
							<li><a
								href="${pageContext.request.contextPath}/board?a=board&page=${currentPage < totalPages ? currentPage + 1 : totalPages }&kwd=${kwd }">▶</a></li>
						</c:if>
						<c:if test="${empty kwd }">
							<li><a
								href="${pageContext.request.contextPath}/board?a=board&page=${currentPage < totalPages ? currentPage + 1 : totalPages }">▶</a></li>
						</c:if>
					</ul>
				</div>
				<div class="bottom">
					<c:if test="${not empty sessionScope.authUser }">
						<a
							href="${pageContext.request.contextPath }/board?a=writeform&no=${sessionScope.authUser.no }"
							id="new-book">글쓰기</a>
					</c:if>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>