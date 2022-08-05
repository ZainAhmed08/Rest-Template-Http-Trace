package com.example.restController.service;

import com.example.restController.entity.Book;
import com.example.restController.repository.BookAuthorRepo;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImplementation implements  BookService{

    @Override
    public List<Book> getBooks() {
        return BookAuthorRepo.bookList;
    }

    @Override
    public Book getBookById(int i) {
        return BookAuthorRepo.bookList.get(i);
    }

    @Override
    public Book addBook(Book book) {
        BookAuthorRepo.sortBookRepo();
        int repoSize = BookAuthorRepo.bookList.size();
        int lastId = Integer.parseInt(BookAuthorRepo.bookList.get(repoSize-1).getBookId());

       System.out.println(repoSize);
      System.out.println(lastId);
        int emptySpace = 1;
        int temp = 0;
        if (repoSize < lastId ) { // which means the records inbetween are deleted
            for (int i = 0 ; i < BookAuthorRepo.bookList.size() ; i++) {
               int space = Integer.parseInt(BookAuthorRepo.bookList.get(i+1).getBookId())-Integer.parseInt(BookAuthorRepo.bookList.get(i).getBookId());
                if (space > emptySpace) {
                    emptySpace = space;
                    temp = i;
                    break;
                }
            }
            System.out.println("book should be added at : "+(temp+2));
            book.setBookId(Integer.toString(temp+2));
            BookAuthorRepo.bookList.add(book);
        return book;
        } else {
            book.setBookId(Integer.toString(lastId+1));
            System.out.println(book.getBookId());
        BookAuthorRepo.bookList.add(book);
        return book;
        }
    }

    @Override
    public Book deleteBookById(int id){
        Boolean recordFound = false;

        recordFound = BookAuthorRepo.bookList.stream().anyMatch(book -> Integer.parseInt(book.getBookId())==id);

        if (recordFound) {
            List<Book> result = BookAuthorRepo.bookList.stream().filter(b -> Integer.parseInt(b.getBookId()) == id).limit(1).collect(Collectors.toList());
            BookAuthorRepo.bookList.remove(result.get(0));
            return result.get(0);
        } else {
            return null;
        }
    }
}
