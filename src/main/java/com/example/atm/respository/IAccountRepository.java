package com.example.atm.respository;

import com.example.atm.dto.UserDTO;
import com.example.atm.model.Account;
import com.example.atm.model.User;

public interface IAccountRepository {
    User getUser(UserDTO userDTO);

    void setActiveUser(User user);

    Account getActiveUserAccount();

    Account setNewBalance(double v);

    boolean isActiveUserAdmin();



    Account depositToUserAccount(String userName, double moneyToDeposit);

    Account getUserAccount(String userName);
}
