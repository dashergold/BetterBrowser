package org.webbrowser.accounts.service;

import org.webbrowser.accounts.Account;
import org.webbrowser.accounts.repository.AccountRepository;
import org.webbrowser.settings.ConfigManager;
import org.webbrowser.util.DialogUtil;

import java.sql.SQLException;

/**
 * @author Axel
 * @since 2026
 */
public class AccountService {
    private static final AccountService instance = new AccountService();
    private final AccountRepository repository = new AccountRepository();
    private final ConfigManager configManager = ConfigManager.getInstance();

    private Account currentAccount;

    private AccountService(){}

    //todo createTableIfAbsent should be used in another place where the config calls for a valid account

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
            //todo test if this error works
            throw new Exception("Wrong verification code was entered");
        }
    }

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

    public void signOut() {
        currentAccount = new Account();
        configManager.editAccountConfig(currentAccount);
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(String email) {

        try {
            repository.createTableIfAbsent();
            currentAccount = repository.findByEmail(email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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

    public static AccountService getInstance() {
        return instance;
    }

}
