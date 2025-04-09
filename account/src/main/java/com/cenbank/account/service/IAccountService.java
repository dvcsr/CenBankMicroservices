package com.cenbank.account.service;


import com.cenbank.account.dto.CustomerDto;


public interface IAccountService {

    void createAccount(CustomerDto customerDto);
    CustomerDto findAccountInfo(String phoneNumber);
    boolean updateAccount(CustomerDto customerDto);
    boolean deleteAccount(String phoneNumber);

}
