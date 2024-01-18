package com.winewizard.winewizard.repository;

import com.winewizard.winewizard.model.Wine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// This is the access wrapper which is reused in the service implementatio

public interface WineRepositoryI extends JpaRepository<Wine, Long> {
    Page<Wine> findByNameContainingIgnoreCase(String searchTerm, Pageable pageable);

    Wine findByNameContainingIgnoreCase(String searchTerm);

    List<Wine> findAllById(Long id);
}
