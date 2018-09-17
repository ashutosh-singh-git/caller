package com.ashu.caller.dao.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Getter
@Setter
@Entity(name = "contacts")
@Table(name = "contacts", indexes = {@Index(name = "Key_contacts_name", columnList = "name"),
        @Index(name = "Key_contacts_mobile", columnList = "mobile"),
        @Index(name = "Key_contacts_userId", columnList = "user_id")})
public class ContactsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "varchar(15)")
    private String mobile;
    private String name;
    @Column(name = "user_id", nullable = false)
    private Long userId;

}
