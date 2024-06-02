<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>board</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board" method="post">
					<input type="text" id="kwd" name="kwd" value="${kwd }">
					<input type="submit" value="찾기">
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
					<c:set var="count" value=" ${fn:length(list) }" />
					<c:forEach items="${list }" var="vo" varStatus="status" begin="0">
						<tr>
							<c:choose>
								<c:when test="${currentPage eq 0 }">
									<td>${status.index + 1 }</td>
								</c:when>
								<c:otherwise>
									<td>${ status.index + (currentPage * 5)  - 4 }</td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${vo.depth eq 0 }">
									<td style="text-align:left; padding-left:${vo.depth * 20 }px ">
										<a
										href="${pageContext.request.contextPath }/board?a=view&no=${vo.no }">${vo.title }</a>
									</td>
								</c:when>
								<c:otherwise>
									<td style="text-align:left; padding-left:${vo.depth * 20 }px ">
										<img
										src='${pageContext.servletContext.contextPath }/assets/images/reply.png' /><a
										href="${pageContext.request.contextPath }/board?a=view&no=${vo.no }">${vo.title }</a>
									</td>
								</c:otherwise>
							</c:choose>
							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<c:choose>
								<c:when test="${authUser.no eq vo.userNo }">
									<td><a
										href="${pageContext.request.contextPath }/board?a=delete&no=${vo.no }"
										class="del">삭제</a></td>
								</c:when>
							</c:choose>
						</tr>
					</c:forEach>
				</table>
				<div class="pager">
					<ul>
						<!-- paging -->
						<c:if test="${currentPage > 5 }">
							<li><a href="${pageContext.request.contextPath }/board?a=board&page=${groupStartNum - 1 }&kwd=${kwd }">◀</a>
							</li>
						</c:if>
						<!-- paging number -->
						<c:forEach var="i" begin="${groupStartNum }" end="${groupEndNum }">
							<c:choose>
								<c:when test="${i > endPageNum }">
									<li>${i }</li>
								</c:when>
								<c:when test="${i == currentPage }">
									<li class="selected">${i }</li>
								</c:when>
								<c:otherwise>
									<li><a
										href="${pageContext.request.contextPath }/board?a=board&page=${i }&kwd=${kwd }">${i }</a>
									</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<!-- next paging number -->
						<c:if test="${endPageNum > groupEndNum }">
							<li><a
								href="${pageContext.request.contextPath }/board?a=board&page=${groupEndNum + 1 }&kwd=${kwd }">▶</a>
							</li>
						</c:if>
					</ul>
				</div>

				<c:choose>
					<c:when test="${not empty authUser }">
						<div class="bottom">
							<a href="${pageContext.request.contextPath }/board?a=writeform&no=${authUser.no }" id="new-book">글쓰기</a>
						</div>
					</c:when>
				</c:choose>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>