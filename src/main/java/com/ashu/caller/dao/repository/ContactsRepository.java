package com.ashu.caller.dao.repository;

import com.ashu.caller.dao.entity.ContactsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactsRepository extends JpaRepository<ContactsEntity, Long> {

    List<ContactsEntity> findAllByMobile(String mobile);
    List<ContactsEntity> findAllByUserId(Long userId);
    ContactsEntity findByUserIdAndMobile(Long userId, String mobile);
    @Query("SELECT p FROM com.instahyre.caller.dao.entity.ContactsEntity p WHERE p.name LIKE '%'||:name||'%' ORDER BY LOCATE(:name, p.name) ASC, p.name ASC")
    List<ContactsEntity> searchByName(@Param("name") String name);
}
