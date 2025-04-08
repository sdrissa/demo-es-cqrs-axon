package com.demo.es_axon_demo.queries.repositories;

import com.demo.es_axon_demo.queries.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
}
