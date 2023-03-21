package com.jake.sns.user.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// TODO: implement
@Getter
@Setter
@Entity
@Table
public class User {
    @Id
    private Long id;

    private String username;
    private String password;
}
