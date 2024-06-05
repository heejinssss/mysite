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

public class ReplyFormAction implements Action {

	@Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long no = Long.valueOf(request.getParameter("no"));

        HttpSession session = request.getSession();
        UserVo authUser = (UserVo) session.getAttribute("authUser");

        if(authUser == null) {
            response.sendRedirect(request.getContextPath()+"/board");
            return;
        }
        BoardVo vo = new BoardDao().findByNo(no);

        request.setAttribute("vo", vo);

        request.getRequestDispatcher("/WEB-INF/views/board/reply.jsp")
               .forward(request, response);
    }
}
