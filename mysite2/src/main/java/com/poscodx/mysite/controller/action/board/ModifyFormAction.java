package com.poscodx.mysite.controller.action.board;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

public class ModifyFormAction implements Action {

	@Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long no = Long.valueOf(request.getParameter("no"));
        BoardVo boardVo = new BoardDao().findByNo(no);

        HttpSession session = request.getSession();
        UserVo authUser = (UserVo) session.getAttribute("authUser");

        if (authUser == null) {
        	response.sendRedirect(request.getContextPath()+"/board");
            return;
        }

        Long userNo = authUser.getNo();
        Long boardVoUserNo = boardVo.getUserNo();

        if(!Objects.equals(userNo, boardVoUserNo)) {
        	response.sendRedirect(request.getContextPath()+"/board");
            return;
        }

        request.setAttribute("vo", boardVo);

        request.getRequestDispatcher("/WEB-INF/views/board/modify.jsp")
               .forward(request, response);
    }
}
