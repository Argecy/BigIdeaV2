package nl.fhict.s3.restclient;

import nl.fhict.s3.restshared.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestClient {

    private static final Logger log = LoggerFactory.getLogger(RestClient.class);

    public static void main(String[] args) {
        UserRestClient client = new UserRestClient();
        //Post new User
        User user = client.authenticateUser(new User("Franc", "Wachtwoord"));
        logUser(user);
    }

    private static void logUser(User user) {
        if (user != null) {
            log.info("{} {}", user.getUsername(), user.getPassword());
        } else {
            log.info("No user found.");
        }
    }
}

