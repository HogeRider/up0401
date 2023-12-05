package com.example.restfullapicandyshop.repositories;

import com.example.restfullapicandyshop.models.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByNameIgnoreCase(String name);

}
