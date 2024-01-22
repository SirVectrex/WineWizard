package com.winewizard.winewizard.service.impl;

import com.winewizard.winewizard.model.Bookmark;
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

    @Override
    public void updateBookmark(Bookmark bookmark) {
        bookmarkRepository.save(bookmark);
    }

    @Override
    public Bookmark saveBookmark(Bookmark bookmark) {
        return bookmarkRepository.save(bookmark);
    }
}
