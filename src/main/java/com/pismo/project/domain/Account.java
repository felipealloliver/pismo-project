package com.pismo.project.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("account_id")
    @ApiModelProperty(value = "Account Id", example = "1")
    private Long accountId;

    @Column(nullable = false)
    @JsonProperty("document_number")
    @ApiModelProperty(value = "Document Number", example = "18178986549")
    private String documentNumber;
}
