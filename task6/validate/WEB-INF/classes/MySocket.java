
import javax.servlet.annotation.WebListener;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.*;

@WebListener
@ServerEndpoint("/validate")
public class MySocket {

    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session) {
        System.out.println(String.format("WebSocket opened: %s", session.getId()));
        peers.add(session);
        System.out.println(peers);
    }

    @OnMessage
    public void onMessage(String txt, Session session) throws IOException {
        System.out.println(String.format("Message received: %s", txt));
        for (Session ses : peers) {
            try {
                ses.getBasicRemote().sendText(txt.toUpperCase());
                System.out.println("Message sent to socket: " + ses.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @OnClose
    public void onClose(CloseReason reason, Session session) {
        System.out.println(peers.toString());
        peers.remove(session);
        System.out
                .println(
                        String.format("Closing a WebSocket (%s) due to %s", session.getId(), reason.getReasonPhrase()));
    }

    @OnError
    public void onError(Session session, Throwable t) {
        t.printStackTrace();
    }
}