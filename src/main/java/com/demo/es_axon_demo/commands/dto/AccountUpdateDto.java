package com.demo.es_axon_demo.commands.dto;

import com.demo.es_axon_demo.enums.AccountStatus;

public record AccountUpdateDto(String accountId, AccountStatus status) {
}
