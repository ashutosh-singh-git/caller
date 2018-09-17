package com.ashu.caller.dao.repository;

import com.ashu.caller.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByMobile(String mobile);

    @Query("SELECT p FROM com.instahyre.caller.dao.entity.UserEntity p WHERE p.name LIKE '%'||:name||'%' ORDER BY LOCATE(:name, p.name) ASC, p.name ASC")
    List<UserEntity> searchByName(@Param("name") String name);

}
