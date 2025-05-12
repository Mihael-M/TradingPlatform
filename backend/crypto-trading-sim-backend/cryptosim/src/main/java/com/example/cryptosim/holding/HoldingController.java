package com.example.cryptosim.holding;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utills.model.Holding;

@RestController
@RequestMapping("/Holding")
public class HoldingController {
    private final HoldingService holdingService;

    public HoldingController(HoldingService holdingService) {
        this.holdingService = holdingService;
    }

    @GetMapping
    public java.util.List<Holding> getAllHoldings() {
        return holdingService.getAllHoldings();
    }

    @GetMapping("/{crypto_id}")
    public org.springframework.http.ResponseEntity<Holding> getHoldingByCryptoId(@org.springframework.web.bind.annotation.PathVariable("crypto_id") String cryptoId) {
        try {
            Holding holding = holdingService.getHolding(cryptoId);
            return org.springframework.http.ResponseEntity.ok(holding);
        } catch (Exception e) {
            return org.springframework.http.ResponseEntity.internalServerError().build();
        }
    }
}
