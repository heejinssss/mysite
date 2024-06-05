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

public class WriteAction implements Action {

	@Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserVo authUser = (UserVo) session.getAttribute("authUser");

        if(authUser == null) {
        	response.sendRedirect(request.getContextPath()+"/board");
            return;
        }

        String title = request.getParameter("title");
        String contents = request.getParameter("contents");
        int maxGroupNo = new BoardDao().findMaxGroupNo();
        Long userNo = authUser.getNo();

        BoardVo boardVo = setBoardVo(maxGroupNo, title, contents, userNo);

        new BoardDao().insert(boardVo);

        response.sendRedirect(request.getContextPath()+"/board?a=board");
    }

    private BoardVo setBoardVo(int maxGroupNo, String title, String contents, Long userNo) {
        BoardVo vo = new BoardVo();
        vo.setTitle(title);
        vo.setContents(contents);
        vo.setHit(0);
        vo.setDepth(1);
        vo.setgNo(maxGroupNo + 1);
        vo.setoNo(1);
        vo.setUserNo(userNo);

        return vo;
    }
}
