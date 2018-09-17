package com.ashu.caller.dao.repository;

import com.ashu.caller.dao.entity.SpamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpamRepository extends JpaRepository<SpamEntity, Long> {

    SpamEntity findByMobile(String mobile);

}
