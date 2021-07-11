package com.pismo.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pismo.project.domain.dto.AccountDto;
import com.pismo.project.domain.dto.TransactionDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureMockMvc
class TransactionControllerTest {
    private final String transactionUri = "/api/v1/transactions";
    private final String accountUri = "/api/v1/accounts";

    @Autowired
    private MockMvc mockMvc;

    @Container
    static PostgreSQLContainer<?> postgreSQL = new PostgreSQLContainer<>("postgres");

    @DynamicPropertySource
    static void postgreSQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQL::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQL::getUsername);
        registry.add("spring.datasource.password", postgreSQL::getPassword);
    }

    @Test
    @DisplayName("given a valid transaction should return accepted")
    public void test01() throws Exception {
        var validAccount = new AccountDto("094129381641");

        var result = this.mockMvc.perform(post(accountUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(validAccount)))
                .andExpect(status().isCreated())
                .andReturn();

        var location = result.getResponse().getHeader("Location");
        assert location != null;

        var validId = Long.valueOf(location.substring(location.lastIndexOf("/") +1));
        var validTransaction = new TransactionDto(validId, 1, BigDecimal.valueOf(10));

        this.mockMvc.perform(post(transactionUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(validTransaction)))
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("given a invalid transaction should return error")
    public void test02() throws Exception {
        var invalidTransaction = new TransactionDto(-999L, 1, BigDecimal.valueOf(0));

        this.mockMvc.perform(post(transactionUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(invalidTransaction)))
                .andExpect(status().isBadRequest());
    }

    private String toJsonString(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(object);
    }
}