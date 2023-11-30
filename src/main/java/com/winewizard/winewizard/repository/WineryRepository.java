package com.winewizard.winewizard.repository;

import com.winewizard.winewizard.model.Wine;
import com.winewizard.winewizard.model.Winery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

// This is the access wrapper which is reused in the service implementation
public interface WineryRepository extends JpaRepository<Winery, Long> {

    Winery findByUrlIdentifier(UUID name);
}
