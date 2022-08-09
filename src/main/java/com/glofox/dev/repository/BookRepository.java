package com.glofox.dev.repository;

import com.glofox.dev.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface BookRepository
    extends JpaRepository<Book, Integer> {

    @Query(value = "SELECT b.BOOK_ID FROM BOOK b WHERE :name=b.NAME AND :date=b.DATE", nativeQuery = true)
    List<Integer> existsWithNameAndDate(String name, Date date);
}
