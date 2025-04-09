package com.cenbank.account.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@SQLDelete(sql = "UPDATE accounts SET deleted = true WHERE account_number=?")
@Where(clause = "deleted=false")
public class Accounts extends BaseEntity {


    private Long customerId;
    @Id
    private Long accountNumber;
    private String accountType;
    private String branchAddress;
}
