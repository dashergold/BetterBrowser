package org.webbrowser.accounts;

/**
 * Represents a user account in the system.
 * <p>>
 * This class holds basic information such as username, password, email and password. It also has a flag for if an account is a valid account or not.
 * @author Axel
 * @since 2026
 */
public class Account {
    /**
     * The account username.
     */
    private String username;
    /**
     * The account email.
     */
    private String email;
    /**
     * The account password
     */
    private String password;
    /**
     * Indicates if the account is a valid account or not.
     */
    private boolean registered = false;

    /**
     * Empty constructor for non-valid accounts. Used for when an account isn't found in the database.
     */
    public Account() {    }
    /**
     * Creates a fully initialized and registered account.
     * @param username the account username.
     * @param email the account email.
     * @param password the account password.
     */
    public Account(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.registered = true;
    }

    /**
     * Returns the username.
     * @return the username.
     */
    public String getUsername() {
        return username;
    }
    /**
     * Returns the email.
     * @return the email.
     */
    public String getEmail() {
        return email;
    }
    /**
     * Returns the password.
     * @return the password.
     */
    public String getPassword() {
        return password;
    }
    /**
     * Indicates if the account is registered or not.
     * @return {@code true} if the account exists, otherwise {@code false}.
     */
    public boolean isRegistered() {
        return registered;
    }
    /**
     * Returns a string representation of the account.
     * @return a string contain username, email and password.
     */
    @Override
    public String toString() {
        return username + " (" + email +") :"+ password;
    }
}
