package com.ecommerce.lifeshop.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.lifeshop.model.User;
import com.ecommerce.lifeshop.model.UserDTO;
import com.ecommerce.lifeshop.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers(@RequestParam("name") Optional<String> name) {
		return service.findAllUser(name);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable Long id) {
		return service.findUser(id);
	}

	@GetMapping("u/{id}")
	public ResponseEntity<User> getById(@PathVariable Long id, @RequestHeader("Authorization") String token) {
		return service.findUserById(id, token);
	}

	@PostMapping("/role/{id_role}")
	public ResponseEntity<UserDTO> postUser(@Valid @RequestBody UserDTO user,
			@PathVariable(value = "id_role") Long id) {
		return service.postUser(user, id);
	}

	@PostMapping
	public ResponseEntity<UserDTO> logUser(@Valid @RequestBody UserDTO user) {
		return service.LogarUser(user);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> putUser(@RequestHeader("Authorization") String token, @Valid @RequestBody User user, @PathVariable Long id) {
		return service.updateUser(token, user, id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
		return service.deleteUser(id);
	}

}