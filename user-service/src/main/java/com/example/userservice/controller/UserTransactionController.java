package com.example.userservice.controller;

import com.example.userservice.dto.TransactionRequestDto;
import com.example.userservice.dto.TransactionResponseDto;
import com.example.userservice.entity.UserTransaction;
import com.example.userservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user/transaction")
public class UserTransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public Mono<TransactionResponseDto> createTransaction(@RequestBody Mono<TransactionRequestDto> requestDtoMono){
        return requestDtoMono.flatMap(this.transactionService::createTransaction);
    }

    @GetMapping
    public Flux<UserTransaction> getByUserId(@RequestParam("userId") int userId){
        return this.transactionService.getByUserId(userId);
    }

}
