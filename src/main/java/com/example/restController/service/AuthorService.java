package com.example.restController.service;

import com.example.restController.entity.Author;
import com.example.restController.entity.Book;

import java.util.List;

public interface AuthorService {
    List<Author> getAuthors();
    Author getAuthorById(int i);
    String addAuthor(Author author);

    Author deleteBookByid(int id);
}
