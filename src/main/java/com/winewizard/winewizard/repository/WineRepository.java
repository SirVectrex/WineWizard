package com.winewizard.winewizard.repository;

import com.winewizard.winewizard.model.Wine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// This is the access wrapper which is reused in the service implementation
public interface WineRepository extends JpaRepository<Wine, Long> {
    Page<Wine> findByNameContainingIgnoreCase(String searchTerm, Pageable pageable);

    List<Wine> findAllById(Long id);
}
