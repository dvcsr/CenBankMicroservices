package com.cenbank.account.repository;

import com.cenbank.account.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByPhoneNumber(String phoneNumber);
    boolean existsByNameAndEmailAndAndPhoneNumber(String name, String email, String phoneNumber);
}
