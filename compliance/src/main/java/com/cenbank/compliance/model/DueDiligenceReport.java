package com.cenbank.compliance.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DueDiligenceReport extends BaseEntity {

    @Id
    private Long reportId;
    private Long customerId;
    private String reportBody;
    private String progress;

}
