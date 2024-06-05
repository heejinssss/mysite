package com.poscodx.mysite.controller.action.board;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ListAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("a");
        if (action == null || action.isEmpty()) {
            request.getSession().removeAttribute("kwd");
            request.getSession().removeAttribute("page");
        }

        int currentPage = getCurrentPage(request);
        int limit = 5;
        int std = (currentPage - 1) * limit;

        String keyword = request.getParameter("kwd");
        List<BoardVo> list;
        int totalPosts;

        BoardDao boardDao = new BoardDao();
        if (keyword!= null &&!keyword.isEmpty()) {
            totalPosts = boardDao.count(keyword);

            list = boardDao.search(keyword, limit, std);
        } else {
            totalPosts = boardDao.count();
            list = BoardDao.findByLimit(limit, std);
        }

        int totalPages = (int) Math.ceil((double) totalPosts / limit);
        int maxPage = 5;
        int startPage = Math.max(1, currentPage - maxPage / 2);
        int endPage = Math.min(totalPages, startPage + maxPage - 1);

        if (endPage - startPage < maxPage - 1) {
            startPage = Math.max(1, endPage - maxPage + 1);
        }

        request.setAttribute("list", list);
        request.setAttribute("totalPosts", totalPosts);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("startPage", startPage);
        request.setAttribute("endPage", endPage);
        request.setAttribute("kwd", keyword);

        request.getRequestDispatcher("/WEB-INF/views/board/list.jsp")
           .forward(request, response);
    }

    private int getCurrentPage(HttpServletRequest request) {
        String pageParam = request.getParameter("page");
        if (pageParam!= null && !pageParam.isEmpty()) {
            return Integer.parseInt(pageParam);
        }
        return 1;
    }
}
