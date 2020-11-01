package com.example.cloudnative.usersws.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="users")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 120, unique = true)
    private String email;
    @Column(nullable = false)
    private String pwd;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault( value = "CURRENT_TIMESTAMP" )
    private Date createdAt;
}
