package com.poscodx.mysite.controller.action.board;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ModifyAction implements Action {

	@Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long no = Long.valueOf(request.getParameter("no"));
        String title = request.getParameter("title");
        String contents = request.getParameter("contents");

        new BoardDao().modify(Long.valueOf(no), title, contents);

        response.sendRedirect(request.getContextPath() + "/board?a=view&no=" + no);
    }
}
