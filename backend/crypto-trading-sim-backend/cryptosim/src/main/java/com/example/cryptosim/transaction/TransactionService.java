package com.example.cryptosim.transaction;

import com.example.cryptosim.account.AccountRepository;
import com.example.cryptosim.converters.ITransactionConverter;
import com.example.cryptosim.create.Create;
import com.example.cryptosim.entity.TransactionEntity;
import com.example.cryptosim.generators.IUUIDGenerator;
import com.example.cryptosim.holding.HodlingRepository;
import utills.model.Transaction;
import utills.model.exceptions.NotEnoughBalanceToBuy;
import utills.model.exceptions.NotEnoughHoldings;
import utills.model.exceptions.TransactionNotFoundException;
import utills.model.types.TransactionType;

import java.util.List;


public class TransactionService implements Service {
    private final TransactionRepository transactionRepository;
    private final HodlingRepository holdingRepository;
    private final AccountRepository accountRepository;
    private final ITransactionConverter transactionConverter;
    private final Create create;
    // TODO: add account repo as dependency

    public TransactionService(TransactionRepository transactionRepository,HodlingRepository hodlingRepository, ITransactionConverter transactionConverter,  AccountRepository accountRepository
    , Create create) {
        this.transactionRepository = transactionRepository;
        this.holdingRepository = hodlingRepository;
        this.transactionConverter = transactionConverter;
        this.accountRepository = accountRepository;
        this.create = create;
    }
    @Override
    public Transaction createTransaction(Transaction transaction) {
        var transactionEntity = this.transactionConverter.convertToEntity(transaction);

        // 1. Check if price is cached, if not, retrieve it from CoinService and cache it.
        double price = getCachedOrFetchPrice(transactionEntity.getCryptoSymbol());
        transactionEntity.setUnitPrice(price);

        // 2. Handle BUY and SELL transaction types
        TransactionType type = transactionEntity.getType();
        if (type == TransactionType.BUY) {
            handleBuyTransaction(transactionEntity, price);
        } else if (type == TransactionType.SELL) {
            handleSellTransaction(transactionEntity, price);
        }

        // 3. After handling the transaction, save it via transactionRepository
        this.transactionRepository.createTransaction(transactionEntity);
        return this.transactionConverter.convertToUser(transactionEntity);
    }

    // Helper: get price from cache or fetch from CoinService and cache it
    private final java.util.Map<String, Double> priceCache = new java.util.HashMap<>();
    private double getCachedOrFetchPrice(String crypto) {
        if (priceCache.containsKey(crypto)) {
            return priceCache.get(crypto);
        }
        // Simulate CoinService call. Replace with real service as needed.
        double fetchedPrice = fetchPriceFromCoinService(crypto);
        priceCache.put(crypto, fetchedPrice);
        return fetchedPrice;
    }
    private double fetchPriceFromCoinService(String crypto) {
        // TODO: Replace with actual CoinService call
        // For now, mock as 100.0 per unit
        return 100.0;
    }

    // Helper for BUY transactions
    private void handleBuyTransaction(TransactionEntity transactionEntity, double price) {
        // Check if account has enough balance
        var account = accountRepository.getAccount(transactionEntity.getAccountId());
        double totalCost = transactionEntity.getQuantity() * price;
        if (account.getBalance() < totalCost) {
            throw new NotEnoughBalanceToBuy("Insufficient balance for BUY transaction");
        }
        // Deduct amount from balance
        account.setBalance(account.getBalance() - totalCost);
        accountRepository.updateBalance(-1 * totalCost);

        // Update or create holding
        var holding = holdingRepository.getHolding(transactionEntity.getCryptoSymbol());
        if (holding == null) {
            // Create new holding
            holdingRepository.createHolding(create.createHolding(transactionEntity));
        } else {
            // Update holding: increase quantity and update price
            holding.setQuantity(holding.getQuantity() + transactionEntity.getQuantity());
            holdingRepository.updateHolding(holding);
        }
    }

    // Helper for SELL transactions
    private void handleSellTransaction(TransactionEntity transactionEntity, double price) {
        // Update holding: decrease quantity
        var holding = holdingRepository.getHolding(transactionEntity.getCryptoSymbol());
        if (holding == null || holding.getQuantity() < transactionEntity.getQuantity()) {
            throw new NotEnoughHoldings("Not enough holdings to SELL");
        }
        holding.setQuantity(holding.getQuantity() - transactionEntity.getQuantity());
        if (holding.getQuantity() <= 0) {
            holdingRepository.deleteHolding(holding.getId());
        } else {
            holdingRepository.updateHolding(holding);
        }
        // Add proceeds to account balance
        var account = accountRepository.getAccount(transactionEntity.getAccountId());
        double proceeds = transactionEntity.getQuantity() * price;
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
