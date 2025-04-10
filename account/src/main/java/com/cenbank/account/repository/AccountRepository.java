package com.cenbank.account.repository;

import com.cenbank.account.model.Accounts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Accounts, Long> {
    List<Accounts> findByCustomerId(Long customerId);
    boolean existsByCustomerIdAndAccountNumber(Long customerId, Long accountNumber);

    @Transactional
    @Modifying
    void deleteByCustomerId(Long customerId);
}
