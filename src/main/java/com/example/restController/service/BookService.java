package com.example.restController.service;

import com.example.restController.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getBooks();
    Book getBookById(int i);
    Book addBook(Book book);

    Book deleteBookById(int id);
}
