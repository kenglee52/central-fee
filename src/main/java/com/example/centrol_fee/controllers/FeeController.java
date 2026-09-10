package com.example.centrol_fee.controllers;

import com.example.centrol_fee.models.Fee;
import com.example.centrol_fee.services.FeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fees")  
public class FeeController {
    
    private final FeeService feeService;
    
    public FeeController(FeeService feeService) {
        this.feeService = feeService;
    }
    @PostMapping

    public ResponseEntity<?> createFee(@Valid @RequestBody Fee fee) {
        try {
            Fee createdFee = feeService.createFee(fee);
            return new ResponseEntity<>(createdFee, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Fee>> getAllFees() {
        return ResponseEntity.ok(feeService.getAllFees());
    }

  @GetMapping("/{id}") 
public ResponseEntity<Fee> getFeeById(@PathVariable Long id) {
    return feeService.getFeeById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
}

    @PutMapping("/{id}") 
    public ResponseEntity<Fee> updateFee(@PathVariable Long id, @Valid @RequestBody Fee fee) {
        Fee updatedFee = feeService.updateFee(id, fee);
        return ResponseEntity.ok(updatedFee);
    }


    
}
