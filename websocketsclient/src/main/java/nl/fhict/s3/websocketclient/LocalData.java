package nl.fhict.s3.websocketclient;

import nl.fhict.s3.websocketshared.User;

public class LocalData {

    private static LocalData instance = null;
    private static User user;

    protected LocalData() {
        // Exists only to defeat instantiation.
    }

    public static LocalData getInstance() {
        if (instance == null) {
            instance = new LocalData();
        }
        return instance;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        LocalData.user = user;
    }
}
