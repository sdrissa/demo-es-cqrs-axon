package org.formation.demoescqrsaxon.commands.controllers;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.formation.demoescqrsaxon.commands.commands.AddAccountCommand;
import org.formation.demoescqrsaxon.commands.dto.AddNewAccountRequestDTO;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/commands/accounts")
public class AccountCommandController {

    private CommandGateway commandGateway;

    public AccountCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("add")
    public CompletableFuture<String> addNewAccount(@RequestBody AddNewAccountRequestDTO request){
        CompletableFuture<String> response = commandGateway.send(AddAccountCommand.builder()
                .id(UUID.randomUUID().toString())
                .initialBalance(request.initialBalance())
                .currency(request.currency()).build());
        return response;
    }
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception exception){
        return exception.getMessage();
    }
}
