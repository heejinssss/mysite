package com.poscodx.mysite.controller.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.PageVo;

public class ListAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        PageVo paging = new PageVo();

        String keyword = request.getParameter("kwd");
        String curPage = request.getParameter("page");

        int currentPage = 1;
        int start = 0;
        int groupStartNum = 0;
        int groupEndNum = 0;
        int endPageNum = 0;
        int pageSize = PageVo.getPageSize();

        if (keyword == null || keyword.isEmpty()) {
            keyword = "";
        }

        if (curPage == null || "null".equals(curPage)) {

        } else {
            currentPage = Integer.parseInt(request.getParameter("page"));
        }

        paging.setGroup(currentPage);
        groupStartNum = paging.getGroupStartNum();
        groupEndNum = paging.getGroupEndNum();

        paging.setLastPageNum(keyword);
        endPageNum = paging.getEndPageNum();

        if (currentPage != 0) {
            start = (currentPage * pageSize) - pageSize;
        } else {
            start = (currentPage * pageSize);
        }

        List<BoardVo> list = new BoardDao().findSearch(keyword, start, pageSize);

        request.setAttribute("currentPage", currentPage);
        request.setAttribute("groupStartNum", groupStartNum);
        request.setAttribute("groupEndNum", groupEndNum);
        request.setAttribute("endPageNum", endPageNum);
        request.setAttribute("list", list);
        request.setAttribute("kwd", keyword);

        request.getRequestDispatcher("/WEB-INF/views/board/list.jsp")
               .forward(request, response);
    }
}
