package com.pismo.project.controller;

import com.pismo.project.config.exception.TransactionException;
import com.pismo.project.domain.dto.TransactionDto;
import com.pismo.project.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @ApiOperation(value = "Create a Transaction")
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "return the account associated with the given id"),
            @ApiResponse(code = 400, message = "something goes wrong, check the response message")
    })
    public ResponseEntity<?> createTransaction(@RequestBody TransactionDto transactionDto) {
        try {
            transactionService.createTransaction(transactionDto);
            return ResponseEntity.accepted().build();
        } catch (TransactionException e) {
            return ResponseEntity.badRequest().body(e.getErrors());
        }
    }
}
