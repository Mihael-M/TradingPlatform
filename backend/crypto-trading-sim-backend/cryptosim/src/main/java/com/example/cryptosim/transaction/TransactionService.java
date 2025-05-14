package com.example.cryptosim.transaction;

import com.example.cryptosim.account.AccountRepository;
import com.example.cryptosim.converters.ITransactionConverter;
import com.example.cryptosim.create.Create;
import com.example.cryptosim.entity.TransactionEntity;
import com.example.cryptosim.holding.HoldingRepository;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utills.model.Transaction;
import utills.model.exceptions.NotEnoughBalanceToBuy;
import utills.model.exceptions.NotEnoughHoldings;
import utills.model.exceptions.TransactionNotFoundException;
import utills.model.types.TransactionType;

import java.util.List;
import java.util.UUID;

@Component
public class TransactionService implements Service {
    private final TransactionRepository transactionRepository;
    private final HoldingRepository holdingRepository;
    private final AccountRepository accountRepository;
    private final ITransactionConverter transactionConverter;
    private final Create create;

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    public TransactionService(TransactionRepository transactionRepository, HoldingRepository holdingRepository, ITransactionConverter transactionConverter, AccountRepository accountRepository
    , Create create) {
        this.transactionRepository = transactionRepository;
        this.holdingRepository = holdingRepository;
        this.transactionConverter = transactionConverter;
        this.accountRepository = accountRepository;
        this.create = create;
    }
    @Override
    public Transaction createTransaction(Transaction transaction) {
        try {
            if (transaction.getId() == null || transaction.getId().isBlank()) {
                transaction.setId(UUID.randomUUID().toString());
            }
            var transactionEntity = this.transactionConverter.convertToEntity(transaction);

            TransactionType type = transactionEntity.getType();
            if (type == TransactionType.BUY) {
                handleBuyTransaction(transactionEntity);
            } else if (type == TransactionType.SELL) {
                handleSellTransaction(transactionEntity);
            }
            this.transactionRepository.createTransaction(transactionEntity);
            return this.transactionConverter.convertToUser(transactionEntity);
        } catch (Exception e) {
            throw e;
        }
    }

    private void handleBuyTransaction(TransactionEntity transactionEntity) {
        var account = accountRepository.getAccount();
        double totalCost = transactionEntity.getQuantity() * transactionEntity.getUnitPrice();


        if (account.getBalance() < totalCost) {
            throw new NotEnoughBalanceToBuy("Insufficient balance for BUY transaction");
        }

        account.setBalance(account.getBalance() - totalCost);
        accountRepository.updateBalance(-1 * totalCost);

        var holding = holdingRepository.getHolding(transactionEntity.getCryptoSymbol());
        if (holding == null) {
            var entity = create.createHolding(transactionEntity);
            holdingRepository.createHolding(entity);
        } else {
            holding.setQuantity(holding.getQuantity() + transactionEntity.getQuantity());
            holding.setTotalValue(holding.getTotalValue() + transactionEntity.getQuantity() * transactionEntity.getUnitPrice());
            holdingRepository.updateHolding(holding);
        }
    }

    private void handleSellTransaction(TransactionEntity transactionEntity) {
        var holding = holdingRepository.getHolding(transactionEntity.getCryptoSymbol());
        if (holding == null || holding.getQuantity() < transactionEntity.getQuantity()) {
            throw new NotEnoughHoldings("Not enough holdings to SELL");
        }
        holding.setQuantity(holding.getQuantity() - transactionEntity.getQuantity());
        holding.setTotalValue(holding.getTotalValue() - transactionEntity.getQuantity() * transactionEntity.getUnitPrice());
        if (holding.getQuantity() <= 0) {
            holdingRepository.deleteHolding(holding.getId());
        } else {
            holdingRepository.updateHolding(holding);
        }
        var account = accountRepository.getAccount();
        double proceeds = transactionEntity.getQuantity() * transactionEntity.getUnitPrice();
        account.setBalance(account.getBalance() + proceeds);
        accountRepository.updateBalance(proceeds);
    }

    @Override
    public Transaction getTransactionById(String id) {
        TransactionEntity transactionEntity =this.transactionRepository.getTransaction(id);
        if(transactionEntity == null) {
            throw new TransactionNotFoundException("Transaction with id " + id + " not found");
        }
        return this.transactionConverter.convertToUser(transactionEntity);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        List<TransactionEntity> transactionEntities = this.transactionRepository.getAllTransactions();
        return transactionEntities.stream()
            .map(transactionConverter::convertToUser)
            .toList();
    }

    @Override
    public boolean deleteAllTransactions() {
       return transactionRepository.deleteAllTransactions();
    }
}
