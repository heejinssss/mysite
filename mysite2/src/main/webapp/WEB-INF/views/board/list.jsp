<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="com.poscodx.mysite.vo.UserVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="kwd_form" action="${pageContext.request.contextPath }/board" method="get">
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
					<c:forEach var="item" items="${list }" varStatus="status">
						<c:set var="cnt" value="${fn:length(list) }" />
						<tr>
							<td>${cnt - status.index }</td>
							<!-- padding으로 게시글과 답글을 구분 -->
							<td style="padding-left:${(item.depth-1) * 20 }px">
								<c:if test="${item.depth > 1 }">
									<img src="${pageContext.request.contextPath}/assets/images/reply.png">
								</c:if>
								<a href="{pageContext.request.contextPath}/board?b=view&no=${item.no }$curPage=${pageVo.curPage }">
									${item.title }
								</a>
							</td>
							<td>${item.writer }</td>
							<td>${item.hit }</td>
							<td>${item.regDate }</td>
							<c:choose>
								<c:when test="${authUser.no == vo.userNo }">
									<td><a href="${pageContext.request.contextPath}/board?a=delete&n=${vo.no}&p=${pageVo.curPage}" class="del">
										삭제
										</a>
									</td>
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

						<c:if test="${pageVo.curPage > 1 }">
							<li><a href="${pageContext.request.contextPath }/board?p=${pageVo.curPage - 1 }&k=${kwd }">
								◀
								</a>
							</li>
						</c:if>

						<c:forEach begin="${pageVo.startPage }" end="${pageVo.endPage }" varStatus="status">
							<li
								<c:if test="${pageVo.curPage == status.index }">class="selected"</c:if>>
								<a href="${pageContext.request.contextPath }/board?p=${status.index }&k=${kwd }">
									${status.index}
								</a>
							</li>
						</c:forEach>

						<c:if test="${pageVo.curPage < pageVo.totalPage }">
							<li><a href="${pageContext.request.contextPath}/board?p=${pageVo.curPage + 1 }&k=${kwd }">
								▶
								</a>
							</li>
						</c:if>

					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<div class="bottom">
					<a href="${pageContext.request.contextPath }/board?a=writeform" id="new-book">글쓰기</a>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>