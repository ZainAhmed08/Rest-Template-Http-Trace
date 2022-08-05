package com.example.restController.repository;

import com.example.restController.entity.Author;
import com.example.restController.entity.Book;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookAuthorRepo {

    public static  List<Book> bookList = new ArrayList<>();
    public static  List<Author> authorList = new ArrayList<>();

    @PostConstruct
    public void loadData(){
        bookList = new ArrayList<>();
        authorList = new ArrayList<>();

        authorList.add(new Author(1,"zain",10));
        authorList.add(new Author(2,"sanath",12));
        authorList.add(new Author(3,"sean harper",13));

        bookList.add(new Book("1","core java","1200","a book about java ",authorList.get(0)));
        bookList.add(new Book("2","Advanced java","1300","a book about Advanced java ",authorList.get(1)));
        bookList.add(new Book("3","Html css js","2000","getting started with web development ",authorList.get(2)));
    }

    public static void sortBookRepo(){
        Collections.sort(bookList, Comparator.comparing(Book::getBookId));
    }
}
