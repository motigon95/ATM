package com.example.atm.respository;

import com.example.atm.dto.UserDTO;
import com.example.atm.exceptions.UserNotFoundException;
import com.example.atm.model.Account;
import com.example.atm.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountRepository implements IAccountRepository {

    User activeUser;

    List<User> userList = loadUsers();
    List<Account> accountList = loadAccounts();

    private List<Account> loadAccounts() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(new User("Antonio Gomez Sar", "antonio.gom", "1234"), 100.00));
        accounts.add(new Account(new User("Ana Garcia Garcia", "ana.gar", "0007"), 100.00));
        accounts.add(new Account(new User("Carla Chaflan Tellido", "Carla.chaf", "7788"), 100.00));
        return accounts;
    }

    public List<User> loadUsers(){
        List<User> userList = new ArrayList<>();
        userList.add(new User("Antonio Gomez Sar", "antonio.gom", "1234"));
        userList.add(new User("Ana Garcia Garcia", "ana.gar", "0007"));
        userList.add(new User("Carla Chaflan Tellido", "Carla.chaf", "7788"));
        userList.add(new User("ADMIN", "admin", "0000"));
        return userList;
    }


    @Override
    public User getUser(UserDTO userDTO) {
        return userList.stream().filter(user -> user.getUserName().equals(userDTO.getUserName())).findFirst().orElseThrow(() ->new UserNotFoundException(userDTO.getUserName()));
    }

    @Override
    public void setActiveUser(User user) {
        activeUser = user;
    }

    @Override
    public Account getActiveUserAccount() {
        return accountList.stream().filter(account -> account.getUser().getUserName().equals(activeUser.getUserName())).findFirst().orElseThrow(() ->new UserNotFoundException(""));
    }

    @Override
    public Account setNewBalance(double newBalance) {
        getActiveUserAccount().setBalance(newBalance);
        return getActiveUserAccount();
    }

    @Override
    public boolean isActiveUserAdmin() {
        return activeUser.getUserName().equals("admin");
    }

    @Override
    public Account depositToUserAccount(String userName, double newBalance) {
        Account userAccount = accountList.stream().filter(account -> account.getUser().getUserName().equals(userName)).findFirst().orElseThrow(() ->new UserNotFoundException(userName));
        if(userAccount!=null){
            userAccount.setBalance(newBalance);
            return userAccount;
        }
        return new Account();
    }

    @Override
    public Account getUserAccount(String userName) {
        return accountList.stream().filter(account -> account.getUser().getUserName().equals(userName)).findFirst().orElseThrow(() ->new UserNotFoundException(userName));
    }


}
