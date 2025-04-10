package com.cenbank.account.service.impl;

import com.cenbank.account.constants.AccountsConstants;
import com.cenbank.account.dto.AccountsDto;
import com.cenbank.account.dto.CustomerDto;
import com.cenbank.account.dto.SummaryDto;
import com.cenbank.account.exception.CustomerAlreadyExistsException;
import com.cenbank.account.exception.ResourceNotFoundException;
import com.cenbank.account.mapper.AccountsMapper;
import com.cenbank.account.mapper.CustomerMapper;
import com.cenbank.account.model.Accounts;
import com.cenbank.account.model.Customer;
import com.cenbank.account.repository.AccountRepository;
import com.cenbank.account.repository.CustomerRepository;
import com.cenbank.account.service.IAccountService;
import com.cenbank.account.service.client.ComplianceFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private ComplianceFeignClient complianceFeignClient;

    @Override
    public void createAccount(CustomerDto customerDto) {
        //first block of code, fail fast approach
        Optional<Customer> existingAcc = customerRepository.findByPhoneNumber(customerDto.getPhoneNumber());
        if (existingAcc.isPresent()) {
            throw new CustomerAlreadyExistsException("Duplicate: Customer already exist with phone number: " + customerDto.getPhoneNumber());
        }


        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Customer savedCustomer = customerRepository.save(customer);

        //link account microservice to compliance microservice
        createCustomerNewSummary(savedCustomer);

        //link customer to account: every account owned by a customer
        accountRepository.save(createNewAccount(savedCustomer));
    }

    @Override
    public CustomerDto findAccountInfo(String phoneNumber) {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new ResourceNotFoundException("Personal information", "phone number:", phoneNumber)
        );
        List<Accounts> accountList = accountRepository.findByCustomerId(customer.getCustomerId());
        if (accountList.isEmpty()) {
            throw new ResourceNotFoundException("Account", "customer id: ", customer.getCustomerId().toString());
        }

        List<AccountsDto> accountsDtoList = accountList.stream().map(
                    account -> AccountsDto.builder().accountNumber(account.getAccountNumber())
                                                    .accountType(account.getAccountType())
                                                .branchAddress(account.getBranchAddress())
                                                .build())
                .collect(Collectors.toList());

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDtoList(accountsDtoList);

        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        List<AccountsDto> accountsDtoList = customerDto.getAccountsDtoList();

        if (!accountsDtoList.isEmpty()) {
            List<Accounts> accountsList = accountsDtoList.stream().map(
                    accountsDto -> {
                        Accounts accounts = accountRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                                () -> new ResourceNotFoundException("Account", "Account number: ", accountsDto.getAccountNumber().toString()));
                        AccountsMapper.mapToAccounts(accountsDto, accounts); //populate account from update form
                        return accounts;
                    }
            ).collect(Collectors.toList());

             accountsList = accountRepository.saveAll(accountsList);
            Long customerId = accountsList.get(0).getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "Customer id: ", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto, customer);
            customer = customerRepository.save(customer);
            isUpdated = true;
            return isUpdated;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String phoneNumber) {
        boolean isDeleted = false;
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new ResourceNotFoundException("Personal information", "phone number: ", phoneNumber)
        );
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        isDeleted = true;
        return isDeleted;
    }


    private Accounts createNewAccount(Customer customer) {
        Accounts newAccounts = new Accounts();
        newAccounts.setCustomerId(customer.getCustomerId());
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);

        return newAccount;
    }

    private void createCustomerNewSummary(Customer customer) {
        SummaryDto newSummaryDto = new SummaryDto();
        newSummaryDto.setCustomerId(customer.getCustomerId().toString());
        newSummaryDto.setKycStatus("CLEAN");
        complianceFeignClient.createSummary(newSummaryDto);
    }
}
