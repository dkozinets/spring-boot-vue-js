package com.dkozinets.task.rest;

import com.dkozinets.task.exception.BillingException;
import com.dkozinets.task.storage.TransactionRepository;
import com.dkozinets.task.storage.UserRepository;
import com.dkozinets.task.storage.entity.Transaction;
import com.dkozinets.task.storage.entity.Type;
import com.dkozinets.task.storage.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.PositiveOrZero;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.dkozinets.task.storage.DataLoader.USER_FULL_NAME;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @GetMapping
    public List<TransactionResponse> getAllTransaction() {
        return StreamSupport.stream(transactionRepository.findAll().spliterator(), false)
                .map(transaction -> TransactionResponse.builder()
                        .amount(transaction.getAmount())
                        .effectiveDate(transaction.getEffectiveDate().toString())
                        .type(transaction.getType())
                        .build()).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<?> commitTransaction(@RequestBody @Validated TransactionBody transactionBody) {
        final User user = userRepository.findByFullName(USER_FULL_NAME);

        final int amount = transactionBody.getAmount();

        if (transactionBody.getType() == Type.CREDIT) {
            user.setValue(user.getValue() + amount);
        } else if (user.getValue() - amount < 0)
            throw new BillingException("You don't have enough money");
        else {
            user.setValue(user.getValue() - amount);
        }

        final Transaction transaction = Transaction.builder()
                .type(transactionBody.getType())
                .amount(amount)
                .effectiveDate(Instant.now())
                .build();

        transactionRepository.save(transaction);

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class TransactionBody {
        private Type type;
        @PositiveOrZero(message = "Please use positive numbers only")
        private int amount;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class TransactionResponse {
        private Type type;
        private int amount;
        private String effectiveDate;
    }
}
