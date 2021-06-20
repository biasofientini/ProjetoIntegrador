package com.ecommerce.lifeshop.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.lifeshop.model.Role;
import com.ecommerce.lifeshop.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository repositoryR;
	
	Role roleAdmin = new Role("ROLE_ADMIN");
	Role roleUser = new Role("ROLE_USER");
	
	public void saveRoles() {
	    Optional<Role> role = repositoryR.findByRole("ROLE_ADMIN");
	    if(role.isEmpty()) {
			repositoryR.save(roleAdmin);
			repositoryR.save(roleUser);
	    }
	}

}
