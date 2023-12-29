package com.winewizard.winewizard.repository;

import com.winewizard.winewizard.model.Wine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// This is the access wrapper which is reused in the service implementation
public interface WineRepository extends JpaRepository<Wine, Long> {
    List<Wine> findByNameContainingIgnoreCase(String searchTerm);
}
