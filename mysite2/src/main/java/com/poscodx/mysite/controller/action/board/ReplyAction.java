package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

public class ReplyAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        UserVo authUser = (UserVo) session.getAttribute("authUser");

        int groupNo = Integer.parseInt(request.getParameter("groupNo"));
        int orderNo = Integer.parseInt(request.getParameter("orderNo"));
        int depth = Integer.parseInt(request.getParameter("depth"));

        String title = request.getParameter("title");
        String contents = request.getParameter("contents");

        BoardVo vo = new BoardVo();
        int countDepth = BoardDao.findDepth(groupNo, depth);
        int countNextDepth = BoardDao.findNextDepth(groupNo, depth);

        if (countDepth != 0 || (countNextDepth != 0 && countDepth == 0)) {
            new BoardDao().updateOrder(groupNo, orderNo);
            vo.setOrderNo(orderNo + 1);
        } else {
            vo.setOrderNo(orderNo + 1);
        }

        vo.setTitle(title);
        vo.setContents(contents);
        vo.setHit(0);
        vo.setGroupNo(groupNo);
        vo.setDepth(depth + 1);
        vo.setUserNo(authUser.getNo());

        new BoardDao().insert(vo);

        response.sendRedirect(request.getContextPath() + "/board?a=board");
    }
}
