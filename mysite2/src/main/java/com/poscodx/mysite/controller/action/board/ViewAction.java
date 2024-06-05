package com.poscodx.mysite.controller.action.board;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ViewAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long no = Long.valueOf(request.getParameter("no"));

        BoardVo boardVo = new BoardDao().findByNo(no);

        String cookieName = "viewed_" + no;

        Cookie[] cookies = request.getCookies();
        boolean found = false;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            new BoardDao().updateHit(no);

            Cookie newCookie = new Cookie(cookieName, "true");
            newCookie.setMaxAge(24 * 60 * 60);  // 1일
            newCookie.setPath(request.getContextPath());
            response.addCookie(newCookie);
        }

        request.setAttribute("title", boardVo.getTitle());
        request.setAttribute("contents", boardVo.getContents());
        request.setAttribute("no", no);
        request.setAttribute("userNo", boardVo.getUserNo());
        request.setAttribute("gNo", boardVo.getgNo());
        request.setAttribute("oNo", boardVo.getoNo());
        request.setAttribute("depth", boardVo.getDepth());

        String kwd = request.getParameter("kwd");
        if (kwd != null && !kwd.isEmpty()) {
        	request.getSession().setAttribute("kwd", kwd);
        }

        String currentPage = request.getParameter("page");
        if (currentPage == null || currentPage.isEmpty()) {
            currentPage = "1";
        }

        request.setAttribute("currentPage", currentPage);

        request.getRequestDispatcher("/WEB-INF/views/board/view.jsp")
               .forward(request, response);
    }
}
