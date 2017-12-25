package com.mycorp.repository;

import org.springframework.data.repository.CrudRepository;

import com.mycorp.model.auth.User;


public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
