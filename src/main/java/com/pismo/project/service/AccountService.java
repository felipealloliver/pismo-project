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
        try {
            return accountRepository.save(accountDto.toAccount());
        } catch (Exception e) {
            throw new CannotSaveException("Não foi possível salvar a nova conta, contacte o suporte");
        }
    }

    public Account getById(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(NotFoundException::new);
    }
}
