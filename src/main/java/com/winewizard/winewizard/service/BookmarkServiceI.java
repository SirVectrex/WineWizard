package com.winewizard.winewizard.service;

import com.winewizard.winewizard.model.Bookmark;
import com.winewizard.winewizard.model.Rating;

import java.util.List;

public interface BookmarkServiceI {

    void deleteBookmarksByUserId(Long user_id);

    List<Bookmark> getAllBookmarksByUserId(Long userId);


    void updateBookmark(Bookmark bookmark);

    Bookmark saveBookmark(Bookmark bookmark);
}
