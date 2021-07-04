package com.ecommerce.lifeshop.model;

import java.util.List;
import java.util.stream.Collectors;

//Data Transfer Object
public class UserDTO {

	public String name;
	
	public String email;

	public String password;
	
	public String token;
	
	public String phone;
	
	public String zipCode;
	
	public String address;
	
	public static UserDTO convert(User user) {
		UserDTO userdto = new UserDTO();
		userdto.name = user.getName();
		userdto.email = user.getEmail();
		userdto.password = user.getPassword();
		userdto.token = user.getToken();
		userdto.zipCode = user.getZipCode();
		userdto.phone = user.getPhone();
		userdto.address = user.getAddress();
		return userdto;
	}
	
	public static List<UserDTO> convertList(List<User> users){
		return users.stream().map(u -> UserDTO.convert(u)).collect(Collectors.toList());
	}

}