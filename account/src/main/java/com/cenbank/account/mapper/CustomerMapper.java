package com.cenbank.account.mapper;

import com.cenbank.account.dto.*;
import com.cenbank.account.model.Accounts;
import com.cenbank.account.model.Customer;

import java.util.List;

public class CustomerMapper {

    public static CustomerDto mapToCustomerDto(Customer customer, CustomerDto customerDto) {
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        return customerDto;
    }

    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer) {
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        return customer;
    }

    public static GetFullCustomerReportDto mapToGetFullCustomerReportDto(Customer customer, List<AccountsDto> accounts,
                                                                         GetSummaryDto getSummaryDto, List<GetReportDto> getReportDtoList) {
        GetFullCustomerReportDto getFullCustomerReportDto = new GetFullCustomerReportDto();
        getFullCustomerReportDto.setName(customer.getName());
        getFullCustomerReportDto.setEmail(customer.getEmail());
        getFullCustomerReportDto.setPhoneNumber(customer.getPhoneNumber());
        getFullCustomerReportDto.setAccountsDtoList(accounts);
        getFullCustomerReportDto.setGetSummaryDto(getSummaryDto);
        getFullCustomerReportDto.setReportDtoList(getReportDtoList);
        return getFullCustomerReportDto;
    }

}