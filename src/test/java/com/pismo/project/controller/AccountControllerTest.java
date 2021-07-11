package com.pismo.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pismo.project.domain.dto.AccountDto;
import com.pismo.project.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureMockMvc
class AccountControllerTest {

    private final String uri = "/api/v1/accounts";

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
    @DisplayName("create account with valid information should return success")
    public void test01() throws Exception {
        var validAccount = new AccountDto("094129381641");

        this.mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(validAccount)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("create account with invalid information should return error")
    public void test02() throws Exception {
        var invalidAccount = new AccountDto("");

        this.mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(invalidAccount)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("given an existing id, should return success with the related account")
    public void test03() throws Exception {
        var validAccount = new AccountDto("094129381641");

        MvcResult result = this.mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(validAccount)))
                .andExpect(status().isCreated())
                .andReturn();

        var location = result.getResponse().getHeader("Location");
        assert location != null;

        var validId = location.substring(location.lastIndexOf("/") +1);

        this.mockMvc.perform(get(uri + "/" + validId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.account_id").value(validId));
    }

    @Test
    @DisplayName("given a non existing id, should return not found error")
    public void test04() throws Exception {
        var invalidId = "-999";

        this.mockMvc.perform(get(uri + "/" + invalidId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private String toJsonString(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(object);
    }
}