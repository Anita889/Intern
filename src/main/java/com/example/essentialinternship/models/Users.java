package com.example.essentialinternship.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDateTime dateOfBirth;
    private String address;
    private String contactNumber;
    @OneToMany(mappedBy="users",cascade = CascadeType.ALL)
    private List<Accounts> accounts;
}

