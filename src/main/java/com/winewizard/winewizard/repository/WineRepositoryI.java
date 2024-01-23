package com.winewizard.winewizard.repository;

import com.winewizard.winewizard.model.Wine;
import com.winewizard.winewizard.model.Winery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// This is the access wrapper which is reused in the service implementatio

public interface WineRepositoryI extends JpaRepository<Wine, Long> {
    Page<Wine> findByNameContainingIgnoreCase(String searchTerm, Pageable pageable);

    Wine findByNameContainingIgnoreCase(String searchTerm);

    List<Wine> findAllById(Long id);

    List<Wine> getWinesByWinery(Winery winery);

    Wine findByEan(long l);
}
