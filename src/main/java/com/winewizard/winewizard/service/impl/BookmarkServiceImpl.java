package com.winewizard.winewizard.service.impl;

import com.winewizard.winewizard.repository.BookmarkRepository;
import com.winewizard.winewizard.service.BookmarkServiceI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
