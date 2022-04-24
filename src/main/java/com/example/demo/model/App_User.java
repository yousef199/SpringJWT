package com.example.demo.model;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Data @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class App_User {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique = true)
	private String name;
	private String username;
	private String password;
	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<Role> roles;
}
