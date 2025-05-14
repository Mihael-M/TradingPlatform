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

    @GetMapping("/account")
    public ResponseEntity<Map<String, Object>> getAccount() {
        try{
            double balance = accountService.getBalance();
            String email = accountService.getAccount().getEmail();
            String id = accountService.getAccount().getId();
            Map<String, Object> response = new HashMap<>();
            response.put("id", id);
            response.put("balance", balance);
            response.put("email", email);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/balance")
    public ResponseEntity<Map<String, Double>> getBalance() {
        try {
            double balance = accountService.getBalance();
            Map<String, Double> response = new HashMap<>();
            response.put("balance", balance);
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

    @PostMapping("/reset")
    public ResponseEntity<Map<String, String>> resetAccount() {
        try {
            boolean success = accountService.resetAccount();
            String encoded = String.valueOf(success);
            Map<String, String> response = new HashMap<>();
            response.put("reset", encoded);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
