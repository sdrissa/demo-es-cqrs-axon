package com.demo.es_axon_demo.commands.controllers;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import com.demo.es_axon_demo.commands.commands.AccountUpdateCommand;
import com.demo.es_axon_demo.commands.dto.AccountUpdateDto;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.web.bind.annotation.*;

import com.demo.es_axon_demo.commands.commands.AccountCreateCommand;
import com.demo.es_axon_demo.commands.commands.AccountCreditCommand;
import com.demo.es_axon_demo.commands.commands.AccountDebitCommand;
import com.demo.es_axon_demo.commands.dto.AccountCreateRequestDto;
import com.demo.es_axon_demo.commands.dto.CreditAccountDto;
import com.demo.es_axon_demo.commands.dto.DebitAccountDto;

@RestController
@RequestMapping("/products")
public class AccountCommandController {

    private CommandGateway commandGateway;

    private EventStore eventStore;



    public AccountCommandController(CommandGateway commandGateway,EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    @PostMapping("/add")
    public CompletableFuture<String>  createAccount(@RequestBody AccountCreateRequestDto request){
        return commandGateway.send(new AccountCreateCommand(
            UUID.randomUUID().toString(),
            request.initialBalance(),
            request.currency()
        ));
               
    }

    @ExceptionHandler()
    public String exceptionHandler(Exception exception){
        return exception.getMessage();
    }

    @GetMapping("/{accountId}")
    public Stream visualisation(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();
    }
    

    @PostMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountDto request){

        return commandGateway.send(new AccountDebitCommand(
            request.accountId(),
            request.amount()
        ));
    }

    @PostMapping("credit")
    public CompletableFuture<String> credit(@RequestBody CreditAccountDto creditAccountDto){

        return commandGateway.send(new AccountCreditCommand(
            creditAccountDto.accountId(),
            creditAccountDto.amount()
        ));

    }

    @PutMapping("/updateStatus")
    public CompletableFuture<String> updateStatus(AccountUpdateDto request){

       return commandGateway.send(AccountUpdateCommand.builder()
                        .accountId(request.accountId())
                        .status(request.status())
                .build());

    }
}
