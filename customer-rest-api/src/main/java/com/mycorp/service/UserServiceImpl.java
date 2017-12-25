package com.mycorp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycorp.model.auth.User;
import com.mycorp.repository.UserRepository;

@Service
public class UserServiceImpl {
	@Autowired
	private UserRepository userRepository;

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	public User findByUsername(String username) {
		log.info("findByUsername {}", username);
		return userRepository.findByUsername(username);
	}

	public List<User> findAllUsers() {
		log.info("findAllUsers");
		return (List<User>) userRepository.findAll();
	}

}
