package com.ecommerce.lifeshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.lifeshop.service.RoleService;


@SpringBootApplication
@RestController
@RequestMapping("/lifeshop")
public class LifeshopApplication {

	@Autowired
	private RoleService serviceR;
	
	@GetMapping
	public void setRoles() {
		serviceR.saveRoles();
	}
	
	public static void main(String[] args) {

		SpringApplication.run(LifeshopApplication.class, args);
	}

}
