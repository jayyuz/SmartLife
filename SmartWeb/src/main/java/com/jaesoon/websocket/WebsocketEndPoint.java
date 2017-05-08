package com.jaesoon.websocket;

import com.gaussic.controller.MobileController;
import com.gaussic.controller.Utils;
import com.gaussic.model.UserEntity;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.servlet.http.HttpSession;
import javax.websocket.ClientEndpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

public class WebsocketEndPoint extends TextWebSocketHandler {
    UserEntity userEntity;

    @Override
    protected void handleTextMessage(WebSocketSession session,
                                     TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        userEntity = (UserEntity) session.getAttributes().get(Utils.USER_SESSION_NAME);
        if (!WebSocketManager.isContains(session)) {
            WebSocketManager.addWebSocketSession(userEntity.getUserMobileNo(), session);
        }
        TextMessage returnMessage;
        if (userEntity != null) {
            returnMessage = new TextMessage("用户" + userEntity.getUserMobileNo() + ",你好");
        } else {
            returnMessage = new TextMessage("未登录用户，你好");
        }
        session.sendMessage(returnMessage);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        if (WebSocketManager.isContains(session)) {
            WebSocketManager.removeSession(session);
        }
    }
}