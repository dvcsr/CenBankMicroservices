package com.cenbank.account.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@SQLDelete(sql = "UPDATE customer SET deleted = true WHERE customer_id=?")
@Where(clause = "deleted=false")
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String name;
    private String email;
    private String phoneNumber;
}
