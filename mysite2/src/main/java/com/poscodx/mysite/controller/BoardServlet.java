package com.poscodx.mysite.controller;

import com.poscodx.mysite.controller.action.board.*;


import java.util.Map;

public class BoardServlet extends ActionServlet {
    private static final long serialVersionUID = 1L;

    private Map<String, Action> mapAction = Map.of(
            "view", new ViewAction(),
            "writeform", new WriteFormAction(),
            "write", new WriteAction(),
            "delete", new DeleteAction(),
            "modifyform", new ModifyFormAction(),
            "modify", new ModifyAction(),
            "reply", new ReplyAction(),
            "replyform", new ReplyFormAction(),
            "page", new PageAction()
    );

    @Override
    protected Action getAction(String actionName) {
        return mapAction.getOrDefault(actionName, new ListAction());
    }
}