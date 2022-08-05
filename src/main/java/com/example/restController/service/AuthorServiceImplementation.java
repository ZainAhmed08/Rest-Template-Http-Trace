package com.example.restController.service;

import com.example.restController.entity.Author;
import com.example.restController.entity.Book;
import com.example.restController.repository.BookAuthorRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImplementation  implements  AuthorService{

    @Override
    public List<Author> getAuthors() {
        return BookAuthorRepo.authorList;
    }

    @Override
    public Author getAuthorById(int i) {
        return BookAuthorRepo.authorList.get(i);
    }

    @Override
    public String addAuthor(Author author) {
        BookAuthorRepo.authorList.add(author);
        return "author added";
    }
   
    @Override
    public Author deleteBookByid(int id) {
        Boolean recordFound = false;

        recordFound = BookAuthorRepo.authorList.stream().anyMatch(author -> author.getAuthorId() ==id);

        if (recordFound) {
            List<Author> result = BookAuthorRepo.authorList.stream().filter(b -> b.getAuthorId() == id).limit(1).collect(Collectors.toList());
            Author author  = result.get(0);
            BookAuthorRepo.authorList.remove(result.get(0));
            return author;
        } else {
            return null;
        }
    }
}
