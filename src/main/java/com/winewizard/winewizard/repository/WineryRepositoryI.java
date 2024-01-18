package com.winewizard.winewizard.repository;

import com.winewizard.winewizard.model.Winery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

// This is the access wrapper which is reused in the service implementation
public interface WineryRepositoryI extends JpaRepository<Winery, Long> {

    Winery findByUrlIdentifier(UUID name);

    @Query("SELECT w FROM Winery w JOIN User u on u.id = w.wineryOwnerId WHERE u.login = :ownerName")
    Winery findWineryByWineryOwnerName(@Param("ownerName") String ownerName);
}
