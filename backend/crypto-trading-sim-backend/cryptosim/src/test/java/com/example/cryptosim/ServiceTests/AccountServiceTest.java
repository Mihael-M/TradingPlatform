package com.example.cryptosim.ServiceTests;

import com.example.cryptosim.account.AccountRepository;
import com.example.cryptosim.account.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import utills.model.Account;


public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private Account mockAccount;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockAccount = new Account("1",100.0,"admin@gmail.com");
        mockAccount.setBalance(100.0);

        //when(accountRepository.getAccount(mockAccount.getId())).thenReturn(mockAccount);
    }
}
