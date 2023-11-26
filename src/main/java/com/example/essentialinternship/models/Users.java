package com.example.essentialinternship.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
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
    @JsonIgnore
    private List<Accounts> accounts;
}


