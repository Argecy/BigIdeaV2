package nl.fhict.s3.restserver.data;

import nl.fhict.s3.restshared.User;

import java.util.Collection;
import java.util.HashMap;

public class UserStore {
    private HashMap<String, User> store;
    private static UserStore instance;

    private UserStore(HashMap<String, User> store) {
        this.store = store;
        createUser();
    }

    public void addUser(User user) {
        store.put(user.getUsername(), user);
    }

    public User getUser(String key) {
        return store.get(key);
    }

    public void removeUser(String key) {
        store.remove(key);
    }

    public void replaceUser(User user) {
        store.replace(user.getUsername(), user);
    }

    public Collection<User> getAll() {
        return store.values();
    }

    public static UserStore getInstance() {
        if (instance == null) {
            instance = new UserStore(new HashMap<>());
        }
        return instance;
    }

    private void createUser() {
        User franc = new User("Franc", "Wachtwoord");
        User buurtschap = new User("Kerkakkers", "Wachtwoord");
        User bloemenkweker = new User("Hennie", "Wachtwoord");
        addUser(franc);
        addUser(buurtschap);
        addUser(bloemenkweker);
    }
}
