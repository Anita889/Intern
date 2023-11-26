package com.example.essentialinternship.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "transactions")
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer transactionId;
    private String transactionType;
    private Double amount;
    @UpdateTimestamp
    private Timestamp transactionDate;
    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private Accounts account;
}
