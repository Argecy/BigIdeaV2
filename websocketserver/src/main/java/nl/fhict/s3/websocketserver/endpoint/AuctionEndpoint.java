package nl.fhict.s3.websocketserver.endpoint;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import nl.fhict.s3.websocketshared.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint(value = "/greeter/")
public class AuctionEndpoint {

    private static final Logger log = LoggerFactory.getLogger(AuctionEndpoint.class);
    private static final List<Session> sessions = new ArrayList<>();
    private static final List<Auction> activeAuctions = new ArrayList<>();
    private static final List<BuildingMaterial> activeOffers = new ArrayList<>();
    private Gson gson;

    @OnOpen
    public void onConnect(Session session) {
        log.info("Connected SessionID: {}", session.getId());

        sessions.add(session);
        log.info("Session added. Session count is {}", sessions.size());
    }

    @OnMessage
    public void onText(String message, Session session) {
        log.info("Session ID: {} Received: {}", session.getId(), message);
        handleMessageFromClient(message, session);
    }

    @OnClose
    public void onClose(CloseReason reason, Session session) {
        log.info("Session ID: {} Closed. Reason: {}", session.getId(), reason);
        sessions.remove(session);
    }

    @OnError
    public void onError(Throwable cause, Session session) {
        log.error("Session ID: {} Error: {}", session.getId(), cause.getMessage());
    }

    public void sendMessage(String message, List<Session> list) {
        log.info("[Broadcast] { " + message + " } to:");

        for (Session s : list) {
            try {
                s.getBasicRemote().sendText(message);
                log.info("\t\t >> Client associated with server side session ID: " + s.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("[End of Broadcast]");
    }

    public void sendMessage(String message, Session session) {
        log.info("[Broadcast] { " + message + " } to:");

        try {
            session.getBasicRemote().sendText(message);
            log.info("\t\t >> Client associated with server side session ID: " + session.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("[End of Broadcast]");
    }

    private void handleMessageFromClient(String jsonmessage, Session client) {
        Gson gson = new Gson();
        WebsocketMessage message = null;
        try {
            message = gson.fromJson(jsonmessage, WebsocketMessage.class);
        } catch (JsonSyntaxException e) {
            log.error("[WebSocket ERROR] : cannot parse Json message : " + jsonmessage);
            sendMessage("Message could not be send", client);
            return;
        }

        if (message != null) {
            MessageOperation operation = message.getOperation();
            if (operation != null) {
                switch (operation) {
                    case START_AUCTION:
                        startAuction(message.getContent());
                    case PLACE_BID:
                        sendMessage("Succes", client);
                    case BUY_BULDING_MATERIAL:
                        buyBuildingMaterial(message.getContent());
                    default:
                        break;
                }
            }
        }
    }

    private void startAuction(String json){
        gson = new Gson();
        Auction auction = gson.fromJson(json, Auction.class);
        activeAuctions.add(auction);
        createMessage(activeAuctions);
    }

    private void buyBuildingMaterial(String json){
        gson = new Gson();
        BuildingMaterial material = gson.fromJson(json, BuildingMaterial.class);

        //Create new list to save still active sales
        List<BuildingMaterial> materialList = new ArrayList<>();
        for(BuildingMaterial bM : activeOffers){
            if(!(bM.getName()).equals(material.getName())){
                materialList.add(bM);
            }
        }

        activeOffers.clear();
        activeOffers.addAll(materialList);
        createMessage(activeOffers);
    }

    private void createMessage(Object obj){
        WebsocketMessage message = new WebsocketMessage();
        message.setContent(gson.toJson(obj));
        sendMessage(gson.toJson(message), sessions);
    }
}
