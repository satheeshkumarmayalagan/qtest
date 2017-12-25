package com.mycorp.repository;

import org.springframework.data.repository.CrudRepository;

import com.mycorp.model.auth.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
