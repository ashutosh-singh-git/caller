package com.ashu.caller.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity(name = "user")
@Table(name = "user", indexes = {@Index(name = "UK_user_mobile", columnList = "mobile", unique = true),
        @Index(name = "Key_user_name", columnList = "name")})
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(columnDefinition = "varchar(15)", nullable = false)
    private String mobile;
    @Column(nullable = false)
    private String name;
    private String email;
    @Column(name = "profile_pic")
    private String profilePic;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
}
