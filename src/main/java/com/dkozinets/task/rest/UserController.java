package com.dkozinets.task.rest;

import com.dkozinets.task.storage.UserRepository;
import com.dkozinets.task.storage.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.dkozinets.task.storage.DataLoader.USER_FULL_NAME;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping
    public User getUser() {
        return userRepository.findByFullName(USER_FULL_NAME);
    }
}
