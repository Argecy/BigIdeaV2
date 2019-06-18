package nl.fhict.s3.websocketclient.endpoint;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Observable;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import nl.fhict.s3.websocketshared.BuildingMaterial;
import nl.fhict.s3.websocketshared.MessageOperation;
import nl.fhict.s3.websocketshared.WebsocketMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@javax.websocket.ClientEndpoint
public class ClientEndpoint extends Observable {

    private static final Logger log = LoggerFactory.getLogger(ClientEndpoint.class);
    private static ClientEndpoint instance = null;
    private static final String URI = "ws://localhost:8095/corsoAuction/";
    private Session session;
    private Gson gson;
    private boolean isRunning = false;
    private String message;

    private ClientEndpoint() {
        gson = new Gson();
    }

    public static ClientEndpoint getInstance() {
        if (instance == null) {
            instance = new ClientEndpoint();
            log.info("ClientEndpoint singleton instantiated");
        }
        return instance;
    }

    public void start() {
        if (!isRunning) {
            startClient();
            isRunning = true;
        }
    }

    public void stop() {
        if (isRunning) {
            stopClient();
            isRunning = false;
        }
    }

    @OnOpen
    public void onWebSocketConnect(Session session) {
        log.info("Client open session {}", session.getRequestURI());
        this.session = session;
    }

    @OnMessage
    public void onWebSocketText(String message, Session session) {
        this.message = message;
        log.info("Client message received {}", message);
        handleMessageFromServer(message);
    }

    @OnError
    public void onWebSocketError(Session session, Throwable cause) {
        log.info("Client connection error {}", cause.toString());
    }

    @OnClose
    public void onWebSocketClose(CloseReason reason) {
        log.info("Client close session {} for reason {} ", session.getRequestURI(), reason);
        session = null;
    }

    private void startClient() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(URI));
            log.info("Connected to server at {}", URI);

        } catch (IOException | URISyntaxException | DeploymentException ex) {
            log.error("Error in startClient: {}", ex.getMessage());
        }
    }

    private void stopClient() {
        try {
            session.close();
            log.info("Session closed");

        } catch (IOException ex) {
            log.error("Error in stopClient {}", ex.getMessage());
        }
    }

    private void sendMessage(WebsocketMessage message){
        session.getAsyncRemote().sendText(gson.toJson(message));
    }

    private void handleMessageFromServer(String message){
        WebsocketMessage wsMessage = gson.fromJson(message, WebsocketMessage.class);
        String content = wsMessage.getContent();
        setChanged();
        notifyObservers(content);
    }

    public void addBuildingMaterial(BuildingMaterial buildingMaterial){
        WebsocketMessage wsMessage = new WebsocketMessage();
        wsMessage.setOperation(MessageOperation.ADD_BUILDING_MATERIAL);
        String json = new Gson().toJson(buildingMaterial);
        wsMessage.setContent(json);
        sendMessage(wsMessage);
    }
}