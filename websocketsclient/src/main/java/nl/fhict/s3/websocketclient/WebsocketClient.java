package nl.fhict.s3.websocketclient;

import java.util.Observable;
import java.util.Observer;

import nl.fhict.s3.websocketclient.endpoint.ClientEndpoint;
import nl.fhict.s3.websocketshared.Greeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebsocketClient implements Observer {

    private static final Logger log = LoggerFactory.getLogger(WebsocketClient.class);

    void start() {
        try {
            ClientEndpoint clientEndpoint = ClientEndpoint.getInstance();
            clientEndpoint.addObserver(this);
            clientEndpoint.start();
            log.info("Websocket client started");

            clientEndpoint.sendMessageToServer(new Greeting("Whoohoo", 50));

            clientEndpoint.stop();
            log.info("Websocket client stopped");
        } catch (Exception ex) {
            log.error("Client couldn't start.");
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        log.info("Update called. {}", arg);
    }
}
