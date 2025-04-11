package com.demo.es_axon_demo.queries.controllers;

import com.demo.es_axon_demo.queries.dto.GetAccountOperations;
import com.demo.es_axon_demo.queries.dto.GetAllAccounts;
import com.demo.es_axon_demo.queries.dto.WatchEventQuery;
import com.demo.es_axon_demo.queries.entities.Account;
import com.demo.es_axon_demo.queries.entities.AccountOperation;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("account/query")
public class AccountQueriesController {

    private QueryGateway queryGateway;

    public AccountQueriesController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/liste")
    public CompletableFuture<List<Account>> getAccounts(){
       return queryGateway.query(new GetAllAccounts(), ResponseTypes.multipleInstancesOf(Account.class));
    }

    @GetMapping("/{accountId}")
    public CompletableFuture<List<AccountOperation>> getOperationsForAccount(@PathVariable String accountId){
        return  queryGateway.query(new GetAccountOperations(accountId), ResponseTypes.multipleInstancesOf(AccountOperation.class));
    }

    @GetMapping(value = "/watch/{accountId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AccountOperation> watch(@PathVariable String accountId){
        SubscriptionQueryResult<AccountOperation,AccountOperation> result=
                queryGateway.subscriptionQuery(new WatchEventQuery(accountId),
                ResponseTypes.instanceOf(AccountOperation.class),
                ResponseTypes.instanceOf(AccountOperation.class));
        return result.initialResult().concatWith(result.updates());
    }
}
