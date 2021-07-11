package com.pismo.project.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pismo.project.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
public class AccountDto {
    @NotNull
    @JsonProperty("document_number")
    private String documentNumber;

    public Account toAccount() {
        return new Account(null, documentNumber);
    }
}
