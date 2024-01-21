package com.winewizard.winewizard.repository;

import com.winewizard.winewizard.model.Bookmark;
import com.winewizard.winewizard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findBookmarkByUserId(Long user_id);

    Optional<Bookmark> findByUserIdAndWineId(Long id, Long id1);

    void deleteByUserId(Long user_id);
}
