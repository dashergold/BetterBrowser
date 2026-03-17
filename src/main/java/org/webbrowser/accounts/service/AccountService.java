package org.webbrowser.accounts.service;

import org.webbrowser.accounts.Account;
import org.webbrowser.accounts.repository.AccountRepository;
import org.webbrowser.settings.ConfigManager;
import org.webbrowser.util.DialogUtil;

import java.sql.SQLException;

/**
 * Service layer responsible for handing account related logic.
 * <p>
 * This class acts as a link between controllers and {@link AccountRepository}, managing authentication, registration, session state and the persistence of an account.
 * Implements a singleton to ensure a single shared instance across the application.
 * @author Axel
 * @since 2026
 */
public class AccountService {
    /**
     * A singleton, the single instance of this service.
     */
    private static final AccountService instance = new AccountService();
    /**
     * Repository used for database operations.
     */
    private final AccountRepository repository = new AccountRepository();
    /**
     * The instance of the manager for browser configurations.
     */
    private final ConfigManager configManager = ConfigManager.getInstance();
    /**
     * The currently authenticated account.
     */
    private Account currentAccount;

    /**
     * Private constructor for singleton usage.
     */
    private AccountService(){}

    /**
     * Registers a new account.
     * <p>
     * This method:
     * <ul>
     *     <li>Ensures the database already exists.</li>
     *     <li>Checks if the email is already registered.</li>
     *     <li>Performs email verification.</li>
     *     <li>Saves the account if verification succeeds.</li>
     * </ul>
     * @param account the account to register.
     * @throws Exception if the email is already in use, or verification fails.
     */
    public void register(Account account) throws Exception{
        repository.createTableIfAbsent();
        if(repository.findByEmail(account.getEmail()).isRegistered()) {
            throw new Exception("This email is already linked to an account");
        }
        EmailVerificationService verification = new EmailVerificationService(account.getEmail());
        verification.sendVerification();
        if(verification.isSuccessful()) {
            currentAccount = account;
            configManager.editAccountConfig(currentAccount);
            repository.save(account);
        }
        else {
            throw new Exception("Wrong verification code was entered");
        }
    }

    /**
     * Attempts to log in a user with the given credentials.
     * @param email the users email.
     * @param password the users password.
     * @return the authenticated {@link Account}.
     * @throws Exception if the account doesn't exist, or if the password is incorrect.
     */
    public Account login(String email, String password) throws Exception {
        repository.createTableIfAbsent();
        Account account = repository.findByEmail(email);
        if (!account.isRegistered()) {
            throw new Exception(email + " is not linked to an account");
        }
        if (!account.getPassword().equals(password)) {
            throw new Exception("Invalid password");
        }
        currentAccount = account;
        configManager.editAccountConfig(currentAccount);
        return account;
    }

    /**
     * Signs out the current user.
     * <p>
     * Clears the current account and updates the configuration.
     */
    public void signOut() {
        currentAccount = new Account();
        configManager.editAccountConfig(currentAccount);
    }

    /**
     * Retrieves the current account
     * @return the current account
     */
    public Account getCurrentAccount() {
        return currentAccount;
    }

    /**
     * Sets the current account based on the provided email.
     * @param email the email of the account to load.
     * @throws RuntimeException if a database error occurs.
     */
    public void setCurrentAccount(String email) {
        try {
            repository.createTableIfAbsent();
            currentAccount = repository.findByEmail(email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Deletes the currently logged in account after user confirmation.
     * <p>
     * prompts the user via {@link DialogUtil} before deletion. If confirmed, the account is removed from the database and the user is signed out.
     * @throws RuntimeException if a database error occurs.
     */
    public void deleteAccount() {
        if(DialogUtil.warnUser(currentAccount.getUsername())) {
            try {
                repository.createTableIfAbsent();
                repository.deleteByEmail(currentAccount.getEmail());
                signOut();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * getter for the singleton instance.
     * @return the current instance.
     */
    public static AccountService getInstance() {
        return instance;
    }

}
