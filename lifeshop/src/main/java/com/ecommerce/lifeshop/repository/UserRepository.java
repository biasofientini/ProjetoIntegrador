package com.ecommerce.lifeshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.lifeshop.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findAllByNameContainingIgnoreCase(String name);
	Optional<User> findByEmail(String email);
	Optional<User> findByToken(String token);
}
