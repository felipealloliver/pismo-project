package com.pismo.project.service;

import com.pismo.project.config.exception.CannotSaveException;
import com.pismo.project.config.exception.NotFoundException;
import com.pismo.project.domain.Account;
import com.pismo.project.domain.dto.AccountDto;
import com.pismo.project.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(AccountDto accountDto) {
        if (accountDto.getDocumentNumber() == null || accountDto.getDocumentNumber().trim().equals("")) {
            throw new CannotSaveException("document_number possui valor inválido");
        }

        return accountRepository.save(accountDto.toAccount());
    }

    public Account getById(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(NotFoundException::new);
    }
}
