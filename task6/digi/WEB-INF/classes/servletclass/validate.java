package servletclass;

import javax.servlet.annotation.WebServlet;

import javax.servlet.annotation.WebListener;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;

@WebListener
@ServerEndpoint("/val")
public class validate {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println(String.format("WebSocket opened in digital vault: %s", session.getId()));
        System.out.println("Query string is: " + session.getQueryString());
    }

    @OnMessage
    public void onMessage(String txt, Session session) throws Exception {
        System.out.println(String.format("Message received: %s", txt));
        session.getBasicRemote().sendText(txt.toUpperCase());
    }

    @OnClose
    public void onClose(CloseReason reason, Session session) {
        System.out
                .println(
                        String.format("Closing a WebSocket (%s) due to %s", session.getId(), reason.getReasonPhrase()));
    }

    @OnError
    public void onError(Session session, Throwable t) {
        t.printStackTrace();
    }

}
