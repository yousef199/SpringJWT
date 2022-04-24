package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.App_User;

public interface UserRepo extends JpaRepository<App_User, Long> {
	App_User findByUsername(String name);

}
