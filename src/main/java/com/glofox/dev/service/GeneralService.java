package com.glofox.dev.service;

import com.glofox.dev.model.Book;
import com.glofox.dev.model.Class;

public interface GeneralService {
    Class createClass(Class aClass) throws Exception;

    Book createBooking(Book book) throws Exception;
}
