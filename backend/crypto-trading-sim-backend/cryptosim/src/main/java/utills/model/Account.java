package utills.model;

import com.example.cryptosim.entity.HoldingEntity;

import java.util.List;
import java.util.UUID;

public class Account {
    private final UUID id = UUID.randomUUID();
    private Double balance;
    //private List<HoldingEntity> holdings; maybe!!!

    public Account(Double balance) {
        this.balance = balance;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

}
