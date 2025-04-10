package com.cenbank.account.service;


import com.cenbank.account.dto.CustomerDto;
import com.cenbank.account.dto.GetFullCustomerReportDto;
import com.cenbank.account.dto.GetFullCustomerReportInputDto;


public interface IAccountService {

    void createAccount(CustomerDto customerDto);
    CustomerDto findAccountInfo(String phoneNumber);
    boolean updateAccount(CustomerDto customerDto);
    boolean deleteAccount(String phoneNumber);
    GetFullCustomerReportDto getFullCustomerReport(GetFullCustomerReportInputDto getFullCustomerReportInputDto);

}
