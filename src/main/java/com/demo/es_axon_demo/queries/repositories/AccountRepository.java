package com.demo.es_axon_demo.queries.repositories;

import com.demo.es_axon_demo.queries.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
