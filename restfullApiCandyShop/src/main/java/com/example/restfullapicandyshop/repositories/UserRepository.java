package com.example.restfullapicandyshop.repositories;

import com.example.restfullapicandyshop.models.User;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByLogin(String login);
}
