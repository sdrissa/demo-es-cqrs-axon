package com.demo.es_axon_demo.queries.repositories;

import com.demo.es_axon_demo.queries.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {

    List<AccountOperation> findByAccount_AccountId(String accountId);
}
