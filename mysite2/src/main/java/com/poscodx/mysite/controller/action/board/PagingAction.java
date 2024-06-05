package com.poscodx.mysite.controller.action.board;

import com.poscodx.mysite.controller.ActionServlet.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PagingAction implements Action {

	@Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int currentPage = Integer.parseInt(request.getParameter("page"));
        Integer.parseInt(request.getParameter("page"));

        response.sendRedirect(request.getContextPath() + "/board?a=board");
    }
}

