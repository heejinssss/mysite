package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.poscodx.mysite.controller.ActionServlet.Action;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;

public class ModifyAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String no = request.getParameter("no");
        String boardNo = request.getParameter("no");
        String title = request.getParameter("title");
        String contents = request.getParameter("contents");

        BoardVo vo = new BoardVo();
        vo.setNo(Long.parseLong(boardNo));
        vo.setTitle(title);
        vo.setContents(contents);

        new BoardDao().updateView(Long.parseLong(no), title, contents);
        request.setAttribute("BoardVo", vo);

        response.sendRedirect(request.getContextPath() + "/board?a=board");
    }

}