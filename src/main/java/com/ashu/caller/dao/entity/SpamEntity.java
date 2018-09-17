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

@Getter
@Setter
@Entity(name = "spam")
@Table(name = "spam", indexes = {@Index(name = "Key_spam_mobile", columnList = "mobile"),})
public class SpamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "varchar(15)")
    private String mobile;
    @Column(name = "is_spam", columnDefinition = "bit(1) default b'0'", nullable = false)
    private Boolean isSpam;

}
