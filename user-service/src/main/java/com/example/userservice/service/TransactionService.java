package com.example.userservice.service;

import com.example.userservice.dto.TransactionRequestDto;
import com.example.userservice.dto.TransactionResponseDto;
import com.example.userservice.dto.TransactionStatus;
import com.example.userservice.entity.UserTransaction;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.repository.UserTransactionRepository;
import com.example.userservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTransactionRepository transactionRepository;

    public Mono<TransactionResponseDto> createTransaction(final TransactionRequestDto requestDto){
        return this.userRepository.updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
                        .filter(Boolean::booleanValue)
                        .map(b -> EntityDtoUtil.toEntity(requestDto))
                        .flatMap(this.transactionRepository::save)
                        .map(ut -> EntityDtoUtil.toDto(requestDto, TransactionStatus.APPROVED))
                        .defaultIfEmpty(EntityDtoUtil.toDto(requestDto, TransactionStatus.DECLINED));
    }

    public Flux<UserTransaction> getByUserId(int userId){
        return this.transactionRepository.findByUserId(userId);
    }

}
