package com.winewizard.winewizard.repository;

import com.winewizard.winewizard.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}
