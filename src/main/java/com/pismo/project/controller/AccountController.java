package com.pismo.project.controller;

import com.pismo.project.config.exception.CannotSaveException;
import com.pismo.project.config.exception.ExceptionDto;
import com.pismo.project.config.exception.NotFoundException;
import com.pismo.project.domain.Account;
import com.pismo.project.domain.dto.AccountDto;
import com.pismo.project.service.AccountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ApiOperation(value = "Create Account")
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful created the account, check location header"),
            @ApiResponse(code = 400, message = "something goes wrong, check the response message")
    })
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

    @ApiOperation(value = "Get Account by Id")
    @RequestMapping(value = "{account_id}", method = RequestMethod.GET, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "return the account associated with the given id", response = Account.class),
            @ApiResponse(code = 404, message = "the given id was not located in our database")
    })
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
