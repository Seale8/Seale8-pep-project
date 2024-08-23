package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    public AccountDAO accountDAO;

    /** no-args constructor for creating a new AccountService with a new AccountDAO.*/
    public AccountService(){
        accountDAO = new AccountDAO();
    }
    /**
     * @param account an Account object.
     * @return The persisted Account if the persistence is successful. null if it was not successfully persisted 
     */
    public Account addAccount (Account account) {
        if(!account.getUsername().isEmpty() && account.getPassword().length() >= 4 && accountDAO.getAccountByUsername(account.getUsername()) == null){
            return accountDAO.insertAccount(account);
    }
     return null;
     }

     public Account getAccount (Account account) {
        if(accountDAO.getAccountByUsernameandPassword(account.getUsername(), account.getPassword()) != null){
            return accountDAO.getAccountByUsernameandPassword(account.getUsername(), account.getPassword()) ;
    }
     return null;
     }
    
}
