package com.poscodx.mysite.controller.action.board;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class DeleteAction implements Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long no = Long.valueOf(req.getParameter("no"));
        new BoardDao().deleteByNo(no);

        String currentPage = req.getParameter("page");
        if (currentPage == null || currentPage.isEmpty()) {
            currentPage = "1";
        }

        String kwd = req.getParameter("kwd");
        if (kwd != null && !kwd.isEmpty()) {
            req.getSession().setAttribute("kwd", kwd);
        }

        String redirectURL = req.getContextPath() + "/board?a=board&page=" + currentPage;
        if (kwd != null && !kwd.isEmpty()) {
            redirectURL += "&kwd=" + URLEncoder.encode(kwd, StandardCharsets.UTF_8);
        }

        resp.sendRedirect(redirectURL);
    }
}
