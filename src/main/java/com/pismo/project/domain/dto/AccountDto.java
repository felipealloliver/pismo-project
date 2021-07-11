package com.pismo.project.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pismo.project.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    @NotBlank(message = "document_number precisa ter um valor preenchido válido")
    @JsonProperty("document_number")
    private String documentNumber;

    public Account toAccount() {
        return new Account(null, documentNumber);
    }
}
