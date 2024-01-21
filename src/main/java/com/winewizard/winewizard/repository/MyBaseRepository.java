package com.winewizard.winewizard.repository;

import java.io.Serializable;
import java.util.Optional;


public interface MyBaseRepository<T, ID extends Serializable> {
	
	<S extends T> S save(S entity);
	Optional<T> findById(ID id);
  
	Iterable<T> findAll();
	   
	void delete(T entity);    
}
