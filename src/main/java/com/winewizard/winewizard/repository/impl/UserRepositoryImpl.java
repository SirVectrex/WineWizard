package com.winewizard.winewizard.repository.impl;

import com.winewizard.winewizard.model.User;
import com.winewizard.winewizard.repository.UserRepositoryI;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryImpl extends UserRepositoryI, CrudRepository<User, Long>{

	
	
	
}
