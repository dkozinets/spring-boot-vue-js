package com.dkozinets.task.storage;

import com.dkozinets.task.storage.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * We could use FlyWay or Liquibase instead
 */
@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {
    private final UserRepository userRepository;

    public final static String USER_FULL_NAME = "Test test";

    public void run(ApplicationArguments args) {
        userRepository.save(User.builder().fullName(USER_FULL_NAME).build());
    }
}