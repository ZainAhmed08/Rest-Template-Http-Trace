package com.example.restController.entity;

import org.springframework.stereotype.Component;

public class Book {

    private String bookId;
    private String bookName;
    private String bookPrice;
    private String journal;

    private Author bookAuthor;

    public Book(){}

    public Book(String bookId, String bookName, String bookPrice, String journal, Author bookAuthor) {

        this.bookId = bookId;
        this.bookName = bookName;
        this.bookPrice = bookPrice;
        this.journal = journal;
        this.bookAuthor = bookAuthor;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public Author getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(Author bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

}
