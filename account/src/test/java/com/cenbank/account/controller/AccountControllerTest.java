package com.cenbank.account.controller;

import com.cenbank.account.config.TestConfigurations;
import com.cenbank.account.constants.AccountsConstants;
import com.cenbank.account.dto.CustomerDto;
import com.cenbank.account.exception.CustomerAlreadyExistsException;
import com.cenbank.account.exception.ServiceTimeoutException;
import com.cenbank.account.service.IAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.concurrent.CountDownLatch;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "eureka.client.enabled=false",
        "spring.cloud.config.enabled=false",
        "spring.cloud.discovery.enabled=false"
})
@AutoConfigureMockMvc
@Import(TestConfigurations.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private IAccountService accountService;

    @MockitoBean
    private HelpController helpController;


    @Test
    void whenCreateAccountWithInvalidData_Return400() throws Exception {
        CustomerDto customerDto = createValidCustomer();
        customerDto.setName("");
        customerDto.setEmail("invalid email");
        customerDto.setPhoneNumber("0000");
        customerDto.setCountryId("NZ");


        MvcResult result = mockMvc.perform(post("/api/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.phoneNumber").exists())
                .andDo(print()).andReturn();

        System.out.println("Response: " + result.getResponse().getContentAsString());
    }



    @Test
    void whenCreateAccountWithValidData_Return201() throws Exception {

        CustomerDto validCustomer = createValidCustomer();



        doNothing().when(accountService).createAccount(any(CustomerDto.class));


        mockMvc.perform(post("/api/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCustomer)))
                .andExpect(status().isCreated()) //http-header
                .andExpect(jsonPath("$.statusCode").value(AccountsConstants.STATUS_201))
                .andExpect(jsonPath("$.statusMessage").value(AccountsConstants.MESSAGE_201))
                .andDo(print());


        verify(accountService, times(1)).createAccount(any(CustomerDto.class));
    }

    @Test
    void whenCreateAccountWithExistingPhoneNumber_Return400() throws Exception {

        CustomerDto validCustomer = createValidCustomer();

        doThrow(new CustomerAlreadyExistsException("Duplicate: " +
                "Customer already exist with phone number: " + validCustomer.getPhoneNumber()))
                .when(accountService).createAccount(any(CustomerDto.class));

        mockMvc.perform(post("/api/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validCustomer)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.apiPath").value(containsString("/api/create")))
                .andExpect(jsonPath("$.errorCode").exists())
                .andExpect(jsonPath("$.errorMessage").value(containsString("already exist with phone number")))
                .andExpect(jsonPath("$.errorTime").exists())
                .andDo(print());

        verify(accountService, times(1)).createAccount(any(CustomerDto.class));
    }

    @Test
    void whenServiceTimeOut() throws Exception {

        when(accountService.findAccountInfo(anyString()))
                .thenThrow(new ServiceTimeoutException("Service temporarily unavailable"));

        mockMvc.perform(get("/api/fetch")
                        .param("phoneNumber", "628123456789"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.errorMessage")
                        .value(containsString("Service temporarily unavailable")));
    }

    private CustomerDto createValidCustomer() {
        CustomerDto validCustomer = new CustomerDto();
        validCustomer.setName("John Doe");
        validCustomer.setEmail("john.doe@example.com");
        validCustomer.setPhoneNumber("628123456789");
        validCustomer.setCountryId("ID");
        validCustomer.setAge(25);

        return validCustomer;
    }
}
