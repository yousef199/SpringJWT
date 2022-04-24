package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Role;
import com.example.demo.model.App_User;

//Assume all user names are unique
public interface UserService {
	App_User saveUser(App_User user);
	Role saveRole(Role role);
	void addRoleToUser(String username , String rolename);
	App_User getUser(String username);
	List<App_User> getAllUsers();
}
