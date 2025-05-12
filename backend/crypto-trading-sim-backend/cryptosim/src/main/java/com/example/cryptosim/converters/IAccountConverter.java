package com.example.cryptosim.converters;

import com.example.cryptosim.entity.AccountEntity;
import utills.model.Account;

public interface IAccountConverter {
    Account convertToUser(AccountEntity entity);
    AccountEntity convertToEntity(Account account);
}
