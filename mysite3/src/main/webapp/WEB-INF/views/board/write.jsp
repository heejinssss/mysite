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
	            <form class="board-form" method="post" action="${pageContext.request.contextPath }/board">
	                <c:choose>
	                    <c:when test="${not empty vo }">
	                        <input type="hidden" name="a" value="reply">
	                        <input type="hidden" name="no" value="${vo.no }">
	                        <input type="hidden" name="groupNo" value="${vo.groupNo }">
	                        <input type="hidden" name="orderNo" value="${vo.orderNo }">
	                        <input type="hidden" name="depth" value="${vo.depth }">
	                    </c:when>
	                    <c:otherwise>
	                        <input type="hidden" name="a" value="write">
	                    </c:otherwise>
	                </c:choose>
	                <table class="tbl-ex">
	                    <tr>
	                        <th colspan="2">
	                            <c:choose>
	                                <c:when test="${not empty vo }">답글 달기</c:when>
	                                <c:otherwise>
	                                    글쓰기
	                                </c:otherwise>
	                            </c:choose>
	                        </th>
	                    </tr>
	                    <tr>
	                        <td class="label">제목</td>
	                        <td><input type="text" name="title" value=""></td>
	                    </tr>
	                    <tr>
	                        <td class="label">내용</td>
	                        <td>
	                            <textarea id="content" name="contents"></textarea>
	                        </td>
	                    </tr>
	                </table>
	                <div class="bottom">
	                    <a href="${pageContext.request.contextPath }/board?a=board">취소</a>
	                    <input type="submit" value="등록">
	                </div>
	            </form>
	        </div>
	    </div>
	    <c:import url="/WEB-INF/views/includes/navigation.jsp" />
	    <c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>