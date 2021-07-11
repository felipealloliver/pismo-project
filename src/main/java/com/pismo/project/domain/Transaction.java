package com.pismo.project.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne
    private Account account;

    @Enumerated(EnumType.ORDINAL)
    private OperationType operationType;

    private BigDecimal amount;

    private LocalDateTime eventDate;
}
