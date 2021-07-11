package com.pismo.project.controller;

import com.pismo.project.config.exception.TransactionException;
import com.pismo.project.domain.dto.TransactionDto;
import com.pismo.project.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody TransactionDto transactionDto) {
        try {
            transactionService.createTransaction(transactionDto);
            return ResponseEntity.accepted().build();
        } catch (TransactionException e) {
            return ResponseEntity.badRequest().body(e.getErrors());
        }
    }
}
