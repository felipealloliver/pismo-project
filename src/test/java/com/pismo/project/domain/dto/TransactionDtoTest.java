package com.pismo.project.domain.dto;

import com.pismo.project.domain.OperationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class TransactionDtoTest {
    @Test
    @DisplayName("Given a transaction of type PAGAMENTO should return a transaction with positive amount")
    public void test01() {
        var transactionDto = new TransactionDto(1L, 4, BigDecimal.valueOf(10));
        var transaction = transactionDto.toTransaction();
        Assertions.assertTrue(transaction.getAmount().compareTo(BigDecimal.ZERO) > 0);
        Assertions.assertEquals(transaction.getAmount(), BigDecimal.valueOf(10));
    }

    @Test
    @DisplayName("Given a transaction of type SAQUE should return a transaction with negative amount")
    public void test02() {
        var transactionDto = new TransactionDto(1L, 3, BigDecimal.valueOf(10));
        var transaction = transactionDto.toTransaction();
        Assertions.assertTrue(transaction.getAmount().compareTo(BigDecimal.ZERO) < 0);
        Assertions.assertEquals(transaction.getAmount(), BigDecimal.valueOf(10).multiply(BigDecimal.valueOf(-1)));
    }

    @Test
    @DisplayName("Given a valid transactionDto should mount a transaction with valid values")
    public void test03() {
        var transactionDto = new TransactionDto(1L, 4, BigDecimal.valueOf(10));
        var transaction = transactionDto.toTransaction();

        Assertions.assertEquals(transactionDto.getAccountId(), transaction.getAccount().getAccountId());
        Assertions.assertEquals(OperationType.valueOf(transactionDto.getOperationTypeId()), transaction.getOperationType());
        Assertions.assertEquals(transactionDto.getAmount(), transaction.getAmount());
    }
}