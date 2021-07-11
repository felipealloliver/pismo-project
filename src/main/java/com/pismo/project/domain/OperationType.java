package com.pismo.project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum OperationType {
    COMPRA_A_VISTA(1, "Compra à vista", -1),
    COMPRA_PARCELADA(2, "Compra Parcelada", -1),
    SAQUE(3, "Saque", -1),
    PAGAMENTO(4, "Pagamento", 1);

    private final Integer id;
    private final String description;
    private final int multiply;

    public static OperationType valueOf(Integer id) {
        return Arrays.stream(OperationType.values()).filter(it -> it.id.equals(id)).findFirst().orElseThrow();
    }
}
