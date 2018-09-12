package ardjomand.leonardo.nutrimeal.auth;

import ardjomand.leonardo.nutrimeal.data.KeyClass;

public class User extends KeyClass {

    private String key;
    private String name;
    private String email;
    private String type;

    public User() {
        // Required for Firebase
    }

    public User(String name, String email, String type) {
        this.name = name;
        this.email = email;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
