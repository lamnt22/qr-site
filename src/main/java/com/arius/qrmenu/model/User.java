package com.arius.qrmenu.model;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "users")
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "username")
    private String username;

    @Column (name = "email")
    private String email;

    @Column (name = "password")
    private String password;

    @Column (name = "status")
    private String status;

    @Column (name = "created_at")
    private String createdAt;

    @Column (name = "updated_at")
    private String updatedAt;

    @ManyToMany (fetch = FetchType.EAGER)
    private Collection<Role> role = new ArrayList<Role>();

}
