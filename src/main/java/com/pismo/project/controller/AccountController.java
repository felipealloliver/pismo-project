package com.pismo.project.controller;

import com.pismo.project.config.exception.CannotSaveException;
import com.pismo.project.config.exception.ExceptionDto;
import com.pismo.project.config.exception.NotFoundException;
import com.pismo.project.domain.dto.AccountDto;
import com.pismo.project.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.net.URI;

@Controller
@RequestMapping("api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@Valid @RequestBody AccountDto accountDto, ServletRequest request) {
        try {
            return ResponseEntity.created(URI.create(request.getServletContext().getContextPath()
                            .concat(accountService.createAccount(accountDto).getAccountId().toString())))
                    .build();
        }
        catch (CannotSaveException e) {
            return ResponseEntity.badRequest().body(ExceptionDto.of(e.getMessage()));
        }
    }

    @GetMapping("{account_id}")
    public ResponseEntity<?> getById(@PathVariable("account_id") Long accountId) {
        try {
            return ResponseEntity.ok().body(accountService.getById(accountId));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ExceptionDto.of(e.getMessage()));
        }
    }
}
