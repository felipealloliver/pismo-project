package com.pismo.project.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pismo.project.domain.Account;
import com.pismo.project.domain.OperationType;
import com.pismo.project.domain.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    @JsonProperty("account_id")
    @NotNull(message = "Necessário informar o account_id")
    @Positive(message = "account_id precisa ter um valor positivo válido.")
    private Long accountId;

    @JsonProperty("operation_type_id")
    @NotNull(message = "Necessário informar o operation_type_id")
    @Range(min = 1, max = 4, message = "O valor de operation_type_id precisa ser um valor da tabela de tipos, verifique a documentação")
    private Integer operationTypeId;

    @NotNull(message = "Necessário informar o amount")
    @Positive(message = "o valor de amount deve ser positivo, superior a zero")
    @Digits(integer = 9, fraction = 2, message = "amount deve conter no máximo duas casas decimais")
    private BigDecimal amount;

    public Transaction toTransaction() {
        return Transaction.builder()
                .account(new Account(this.accountId, null))
                .amount(this.amount.multiply(BigDecimal.valueOf(OperationType.valueOf(this.operationTypeId).getMultiply())))
                .eventDate(LocalDateTime.now())
                .operationType(OperationType.valueOf(this.operationTypeId))
                .build();
    }
}
