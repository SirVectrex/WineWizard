package com.winewizard.winewizard.service.impl;

import com.winewizard.winewizard.model.Bookmark;
import com.winewizard.winewizard.model.Rating;
import com.winewizard.winewizard.repository.BookmarkRepository;
import com.winewizard.winewizard.service.BookmarkServiceI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookmarkServiceImpl implements BookmarkServiceI {

    private BookmarkRepository bookmarkRepository;

    public BookmarkServiceImpl(BookmarkRepository bookmarkRepository){
        super();
        this.bookmarkRepository = bookmarkRepository;
    }

    @Transactional
    public void deleteBookmarksByUserId(Long user_id) {
        bookmarkRepository.deleteByUserId(user_id);
    }

    public List<Bookmark> getAllBookmarksByUserId(Long userId){
        return bookmarkRepository.findBookmarkByUserId(userId);
    }

    public List<Bookmark> getBookmarksByUser(long user_id){

        List<Bookmark> bookmarks = getAllBookmarksByUserId(Long.valueOf(user_id));

        // set user password and role to null for security reasons
        for (Bookmark bookmark : bookmarks) {
            bookmark.getUser().setPassword(null);
            bookmark.getUser().setRoles(null);
        }

        if (bookmarks == null) {
            // Handle error, such as returning an error message or status code
            return null;
        }
        return bookmarks;

    }

    @Override
    public void updateBookmark(Bookmark bookmark) {
        bookmarkRepository.save(bookmark);
    }
}
