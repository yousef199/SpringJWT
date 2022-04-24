package com.example.demo.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.model.App_User;
import com.example.demo.model.Role;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class UserRestController {
	private final UserService userService;

	@GetMapping("/users")
	public ResponseEntity<List<App_User>> getUsers() {
		System.out.println("getting all users");
		return ResponseEntity.ok().body(userService.getAllUsers());
	}

	@PostMapping("/user/save")
	public ResponseEntity<App_User> saveUser(@RequestBody App_User user) {
		return ResponseEntity.ok().body(userService.saveUser(user));
	}

	@PostMapping("/role/save")
	public ResponseEntity<App_User> saveRole(@RequestBody App_User user) {
		return ResponseEntity.ok().body(userService.saveUser(user));
	}

	@PostMapping("/role/addRoleToUser")
	public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
		userService.addRoleToUser(form.getUsername(), form.getRolename());
		return ResponseEntity.ok().build();
	}

	@GetMapping("/token/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response)
			throws StreamWriteException, DatabindException, IOException {
		System.out.println("regenerating token");
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				String refresh_token = authorizationHeader.substring("Bearer ".length());
				Algorithm algo = Algorithm.HMAC256("secret".getBytes());
				JWTVerifier verifier = JWT.require(algo).build();
				DecodedJWT decodedJWT = verifier.verify(refresh_token);
				String username = decodedJWT.getSubject();
				App_User user = userService.getUser(username);

				String accessToken = JWT.create().withSubject(user.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis() + 60 * 1000))
						.withIssuer(request.getRequestURL().toString())
						.withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
						.sign(algo);
				System.out.println("access token: " + accessToken);

				// send the tokens with the response
				HashMap<String, String> tokens = new HashMap<>();
				tokens.put("access token", accessToken);
				tokens.put("refresh token", refresh_token);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);
			} catch (Exception e) {
				log.error("Error logging in {}", e.getMessage());
				response.setHeader("error", e.getMessage());
				response.setStatus(HttpStatus.FORBIDDEN.value());

				// send the error back
				HashMap<String, String> tokens = new HashMap<>();
				tokens.put("error", e.getMessage());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);
			}
		} else {
			throw new RuntimeException("Refresh token not found");
		}

	}
}

@Data
class RoleToUserForm {
	private String username;
	private String rolename;
}