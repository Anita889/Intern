package com.example.essentialinternship.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "accounts")
public class Accounts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer accountId;
    @Enumerated(EnumType.STRING)
    private Role accountType;
    private String email;
    private Double balance;
    private String password;
    @CreationTimestamp
    private Timestamp openDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transactions> transactions;
}

