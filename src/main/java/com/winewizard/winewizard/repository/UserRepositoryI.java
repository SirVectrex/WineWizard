package com.winewizard.winewizard.repository;


import com.winewizard.winewizard.model.User;

import java.util.Optional;

public interface UserRepositoryI extends MyBaseRepository<User, Long> {
	
	
	Optional<User> findByLoginIgnoreCase(String login);

	void deleteById(Long userId);
}
