package com.winewizard.winewizard.service.impl;

import com.winewizard.winewizard.repository.BookmarkRepository;
import com.winewizard.winewizard.service.BookmarkServiceI;

public class BookmarkServiceImpl implements BookmarkServiceI {

    private BookmarkRepository bookmarkRepository;

    public BookmarkServiceImpl(BookmarkRepository bookmarkRepository){
        super();
        this.bookmarkRepository = bookmarkRepository;
    }
}
