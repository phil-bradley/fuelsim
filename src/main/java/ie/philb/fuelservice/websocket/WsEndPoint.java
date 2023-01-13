/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.fuelservice.websocket;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

/**
 *
 * @author PBradley
 */
@Component
@ServerEndpoint(value = "/ws/status")
public class WsEndPoint {

    private static final Set<Session> sessions = new HashSet<>();

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Session opened");
        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Session closed");
        sessions.remove(session);
    }

    public static void broadcast(String msg) {

        for (Session session : sessions) {
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException ex) {
                System.err.println("Failed to send message");
                ex.printStackTrace();
            }
        }
    }
}
