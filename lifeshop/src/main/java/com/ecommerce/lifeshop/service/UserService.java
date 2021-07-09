package com.ecommerce.lifeshop.service;

import java.nio.charset.StandardCharsets;



import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.lifeshop.model.Role;
import com.ecommerce.lifeshop.model.User;
import com.ecommerce.lifeshop.model.UserDTO;
import com.ecommerce.lifeshop.repository.RoleRepository;
import com.ecommerce.lifeshop.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	public UserRepository repository;

	@Autowired
	public RoleRepository repositoryR;

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	public ResponseEntity<List<User>> findAllUser(Optional<String> name) {
		if (name.isPresent()) {
			return ResponseEntity.ok().body(repository.findAllByNameContainingIgnoreCase(name.get()));
		}
		return ResponseEntity.ok().body(repository.findAll());
	}
	
	public ResponseEntity<User> findUser(Long id){
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.status(404).build());
	}

	public ResponseEntity<User> findUserById(Long id, String token) {
		Optional<User> user = repository.findById(id);
		if(user.isPresent() && user.get().getToken().equals(token)) {
			return ResponseEntity.ok().body(user.get());
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	public ResponseEntity<List<User>> findUserByName(String name) {
		List<User> users = repository.findAllByNameContainingIgnoreCase(name);
		if (!users.isEmpty()) {
			return ResponseEntity.status(302).body(users);
		} else {
			return ResponseEntity.status(404).build();
		}
	}

	public ResponseEntity<UserDTO> postUser(UserDTO userdto, Long id) {
		Optional<User> user = repository.findByEmail(userdto.email);
		Optional<Role> role = repositoryR.findById(id);
		if (!user.isPresent() && !role.isEmpty()) {
			User userOne = new User();
			userOne.setZipCode(userdto.zipCode);
			userOne.setEmail(userdto.email);
			userOne.setPhone(userdto.phone);
			userOne.setName(userdto.name);
			userOne.setAddress(userdto.address);
			userOne.setPassword(userdto.password);
			userOne.setPoints(0);
			userOne.getRoles().add(role.get());
			String passwordEncoder = encoder.encode(userOne.getPassword());
			userOne.setPassword(passwordEncoder);

			return ResponseEntity.status(HttpStatus.CREATED).body(UserDTO.convert(repository.save(userOne)));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	public ResponseEntity<UserDTO> LogarUser(UserDTO userdto) {
		Optional<User> user = repository.findByEmail(userdto.email);

		if (user.isPresent() && userdto.password != null) {
			if (encoder.matches(userdto.password, user.get().getPassword())) {
				String auth = userdto.email + ":" + userdto.password;

				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
				String authHeader = "Basic " + new String(encodedAuth);

				user.get().setToken(authHeader);
				repository.save(user.get());

				return ResponseEntity.ok(UserDTO.convert(user.get()));
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

	}

	public ResponseEntity<UserDTO> updateUser(String token, User user, Long id) {
		Optional<User> userOne = repository.findById(id);
		Optional<User> userToken = repository.findByToken(token);
		if (userOne.isPresent() && !userOne.get().getPassword().equals(user.getPassword())
				&& userOne.get().equals(userToken.get())) {
			String senhaEncoder = encoder.encode(user.getPassword());
			userOne.get().setPassword(senhaEncoder);
			userOne.get().setZipCode(user.getZipCode());
			userOne.get().setEmail(user.getEmail());
			userOne.get().setAddress(user.getAddress());
			userOne.get().setName(user.getName());
		}
		return ResponseEntity.status(200).body(UserDTO.convert(repository.save(userOne.get())));

	}

	public ResponseEntity<Object> deleteUser(Long id) {
		if (repository.findById(id) != null) {
			repository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

}
