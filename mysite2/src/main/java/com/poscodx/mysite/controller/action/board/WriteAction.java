package com.poscodx.mysite.controller.action.board;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");

		if (authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user?a=login");
			return;
		} else {
			request.setAttribute("authUser", authUser);
		}

		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		Date currentDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String regDate = formatter.format(currentDate);

		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setRegDate(regDate);

		Long userNo = authUser.getNo();
		vo.setUserNo(userNo);

		String no = request.getParameter("no");

		if (no == null || "".equals(no)) {
			vo.setgNo(new BoardDao().getNextGroupNo());
			vo.setDepth(1L);
			vo.setoNo(1L);
		} else {
            BoardVo prevVo = new BoardDao().getBoardByNo(no).get(0);
            vo.setgNo(prevVo.getgNo());
            vo.setoNo(prevVo.getoNo() + 1);
            vo.setDepth(prevVo.getDepth() + 1);
            new BoardDao().Update(prevVo.getgNo(), prevVo.getoNo() + 1);
		}
		
		new BoardDao().insert(vo);
		System.out.println(title + " " + contents + " " + vo.getRegDate() + " " + userNo + " " + vo.getNo() + " " + vo.getgNo() + " " + vo.getoNo() + " " + vo.getDepth());
		response.sendRedirect(request.getContextPath() + "/board");
	}
}
