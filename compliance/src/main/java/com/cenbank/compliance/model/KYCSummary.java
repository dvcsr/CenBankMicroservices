package com.cenbank.compliance.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class KYCSummary extends BaseEntity {
    @Id
    private Long customerId;
    private String kycStatus;
    private LocalDateTime expiredDate;
}
