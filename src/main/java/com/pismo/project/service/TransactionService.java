package com.pismo.project.service;

import com.pismo.project.config.exception.ExceptionDto;
import com.pismo.project.config.exception.NotFoundException;
import com.pismo.project.config.exception.TransactionException;
import com.pismo.project.domain.Transaction;
import com.pismo.project.domain.dto.TransactionDto;
import com.pismo.project.repository.AccountRepository;
import com.pismo.project.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    public void createTransaction(TransactionDto transactionDto) {
        Transaction transaction = transactionDto.toTransaction();
        validateTransaction(transaction);

        transactionRepository.save(transactionDto.toTransaction());
    }

    private void validateTransaction(Transaction transaction) {
        List<ExceptionDto> errors = new ArrayList<>();

        try {
            accountService.getById(transaction.getAccount().getAccountId());
        } catch (NotFoundException e) {
            errors.add(ExceptionDto.of("account_id não localizado"));
        }

        if (errors.size() > 0) {
            throw new TransactionException(errors);
        }
    }
}
