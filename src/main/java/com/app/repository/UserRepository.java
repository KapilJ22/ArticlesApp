package com.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.app.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	public User findByUsername(String username);
}
