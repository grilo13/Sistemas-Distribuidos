package com.example.restservice.repository;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.restservice.model.User;

@Service
public class PopulateDB implements CommandLineRunner{
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	
	public PopulateDB(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		userRepository.deleteAll();
		
		
		User DGS = new User("DGS", passwordEncoder.encode("password"),"DGS");
		User Centro = new User("Centro", passwordEncoder.encode("password"), "CENTRO");
		User utente = new User("João Santos", passwordEncoder.encode("password"), "USER");
		
		List<User> users = Arrays.asList(DGS, Centro, utente);
		
		this.userRepository.saveAll(users);
		
	}
	

}
