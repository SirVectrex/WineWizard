package com.winewizard.winewizard.repository;


import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.model.ZipCode;

import java.util.Optional;

public interface UserRepositoryI extends MyBaseRepository<User, Long> {

	Optional<User> findByLoginIgnoreCase(String login);

	void deleteById(Long userId);

	User save(User user);

	void save(ZipCode zipCode);
}
