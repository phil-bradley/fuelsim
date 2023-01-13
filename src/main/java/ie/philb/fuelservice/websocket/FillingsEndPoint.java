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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author PBradley
 */
@Component
@ServerEndpoint(value = "/ws/fillings")
public class FillingsEndPoint {

    private static final Logger logger = LoggerFactory.getLogger(FillingsEndPoint.class);

    private static final Set<Session> sessions = new HashSet<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

    public static void broadcast(String msg) {

        logger.info("Will broadcast filling {}", msg);

        for (Session session : sessions) {
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException ex) {
                logger.error("Failed to broadcast filling", ex);
            }
        }
    }

}
