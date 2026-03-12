package org.webbrowser.accounts;

public class Account {
    private String username;
    private String email;
    private String password;
    private boolean registered = false;

    public Account() {    }

    public Account(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.registered = true;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    @Override
    public String toString() {
        return username + " (" + email +") :"+ password;
    }
    public boolean isRegistered() {
        return registered;
    }
}
