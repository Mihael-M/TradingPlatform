package com.example.cryptosim.account;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/balance")
    public ResponseEntity<Map<String, String>> getBalance() {
        try {
            double balance = accountService.getBalance();
            String encoded = String.format("%.2f$", balance);
            Map<String, String> response = new HashMap<>();
            response.put("balance", encoded);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/balance/{quantity}")
    public ResponseEntity<Map<String, String>> updateBalance(@PathVariable Double quantity) {
        try {
            accountService.updateBalance(quantity);
            String result = quantity < 0
                    ? String.format("Withdrew %.2f$", quantity)
                    : String.format("Deposited %.2f$", quantity);
            Map<String, String> response = new HashMap<>();
            response.put("success", result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/reset")
    public ResponseEntity<Map<String, String>> resetAccount() {
        try {
            boolean success = accountService.resetAccount();
            String encoded = String.valueOf(success);
            Map<String, String> response = new HashMap<>();
            response.put("reset", encoded);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
