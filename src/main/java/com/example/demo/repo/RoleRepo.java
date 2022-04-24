package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
	Role findByName(String name);

}
