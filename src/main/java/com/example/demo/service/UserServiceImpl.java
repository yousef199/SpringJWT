package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Role;
import com.example.demo.model.App_User;
import com.example.demo.repo.RoleRepo;
import com.example.demo.repo.UserRepo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @AllArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService , UserDetailsService {
	private final UserRepo userRepo;
	private final RoleRepo roleRepo;
	private final PasswordEncoder passwordEncoder;


	@Override
	// look for user 
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		App_User user = userRepo.findByUsername(username);
		if(user == null) {
			log.error("user {} was not found in the database" , username);
			throw new UsernameNotFoundException("user was not found in the database");
		}
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		//put each role in a simple granted authority and add to authorities list 
		user.getRoles().forEach(role ->{
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});

		//Spring security user(not app user)
		return new User(user.getName() , user.getPassword() , authorities);
	}

	@Override
	public App_User saveUser(App_User user) {
		log.info("saving user {} to database" , user.getUsername());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	@Override
	public Role saveRole(Role role) {
		log.info("saving role {} to database" , role.getName());
		return roleRepo.save(role);
	}

	@Override
	public void addRoleToUser(String username, String rolename) {
		log.info("adding role {} to user {} " , rolename , username);
		App_User user = userRepo.findByUsername(username);
		Role role = roleRepo.findByName(rolename);
		user.getRoles().add(role);
	}

	@Override
	public App_User getUser(String username) {
		log.info("fetching user {} from database" , username);
		return userRepo.findByUsername(username);
	}

	@Override
	public List<App_User> getAllUsers() {
		log.info("fetching all users");
		return userRepo.findAll();
	}


}
