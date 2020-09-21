package com.dkozinets.task.storage;

import com.dkozinets.task.storage.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByFullName(String fullName);
}
