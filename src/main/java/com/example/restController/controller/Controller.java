package com.example.restController.controller;

import com.example.restController.configuration.EndPointHitRate;
import com.example.restController.entity.Author;
import com.example.restController.entity.Book;
import com.example.restController.repository.BookAuthorRepo;
import com.example.restController.service.AuthorService;
import com.example.restController.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    BookService bookService;
    @Autowired
    AuthorService authorService;
    @Autowired
    RestTemplate restTemplate;

    AuditResult auditResult;

    List<EndPointHitRate> resultentList = new ArrayList<>();

    @GetMapping("/greet")
    public String greetPeople(){
        return "good morning";
    }

    @GetMapping("/ping")
    public Health getActuatorHealth(){
        String url = "http://localhost:8089/actuator/health";
        restTemplate = new RestTemplate();
        Map result = restTemplate.getForObject(url,Map.class);
        if (result.get("status").toString().equals("UP")) {
            return Health.up().build();
        } else {
        return  Health.down().build();
        }
    }

    @GetMapping("/audit")
    public List getAuditResult(){
       auditResult = new AuditResult();
        System.out.println("made a auditResultObject");
       String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        System.out.println("base url build "+baseUrl);
       HttpTrace httpTrace =restTemplate.getForObject(baseUrl + "/actuator/httptrace", HttpTrace.class);
        System.out.println("HttpTrace object created");
        httpTrace.getTraces().forEach(x->auditResult.addTrace(x));
        System.out.println("made auditResult List : "+auditResult.auditList);

        return auditResult.auditList;
    }

    @GetMapping("/auditing")
    public List<Audit> getAudit(){
        auditResult = new AuditResult();
        HttpTrace httpTrace = restTemplate.getForObject("http://localhost:8089/actuator/httptrace",HttpTrace.class);
        AuditList auditList = new AuditList();
       return auditList.addTraces(httpTrace);
    }

    @GetMapping("/books")
    public List<Book> getAllBooks(){
        return bookService.getBooks();
    }

    @GetMapping("/books/{bookId}")
    public Book getBook(@PathVariable int bookId){
        return bookService.getBookById(bookId);
    }

    @GetMapping("/authors")
    public List<Author> getAllAuthors(){
        return authorService.getAuthors();
    }

    @GetMapping("/authors/{authorId}")
    public Author getAuthorById(@PathVariable int authorId){
        return authorService.getAuthorById(authorId);
    }

    @PostMapping("/authors")
    public Author addAuthor(@RequestBody Author author){
        authorService.addAuthor(new Author(authorService.getAuthors().size()+1,author.getAuthorName(),author.getExperience()));
        System.out.println(authorService.getAuthorById(authorService.getAuthors().size()-1).getAuthorName()+" , "+authorService.getAuthorById(authorService.getAuthors().size()-1).getAuthorId());
        return authorService.getAuthorById(authorService.getAuthors().size()-1);
    }

    @DeleteMapping("/books/{id}")
    public Book deleteBook(@PathVariable int id){
      return   bookService.deleteBookById(id);
    }

    @DeleteMapping("/authors/{id}")
    public Author deleteAuthor(@PathVariable int id){
        return authorService.deleteBookByid(id);
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    @PostMapping("/books")
//    public Book addBooks(@RequestBody DummyBookBean dummyBookBean){
//        Boolean bookAndAuthorExists = false;
//        Boolean authorExists = false;
//     //   System.out.println(dummyBookBean.getBookAuthor()+" ,  "+BookAuthorRepo.bookList.get(BookAuthorRepo.bookList.size()-1).getBookId());
//        if (dummyBookBean.getBookAuthor() > Integer.parseInt(BookAuthorRepo.bookListRead.get(BookAuthorRepo.bookListRead.size()-1).getBookId()+1)) {
//            throw new AuthorNotFoundException(" author not found in the repository please add the author before adding the book ");
//        } else {
//            System.out.println();
//            bookAndAuthorExists = BookAuthorRepo.bookListRead.stream().anyMatch(book -> book.getBookAuthor().getAuthorName().equals(BookAuthorRepo.authorList.get(dummyBookBean.bookAuthor-1).getAuthorName()) && book.getBookName().equals(dummyBookBean.bookName));
//        }
//        if (bookAndAuthorExists) {
//            throw new BookAlreadyExistException(" Book with the author already exists");
//        } else {
//            Book book = new  Book(Integer.toString(BookAuthorRepo.bookList.size()+1),dummyBookBean.getBookName(),dummyBookBean.getBookPrice(),dummyBookBean.getJournal(),BookAuthorRepo.authorList.get(dummyBookBean.getBookAuthor()-1));
//            bookService.addBook(book);
//            return book;
//        }
//    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//    @PostMapping("/books")
//   public Book addBooks(@RequestBody DummyBookBean dummyBookBean){
//        Boolean bookAndAuthorExists = false;
//        int authorRepoLen = BookAuthorRepo.bookListRead.size();
//        if (dummyBookBean.getBookAuthor() > authorRepoLen) {
//            throw new AuthorNotFoundException(" author not found in the repository please add the author before adding the book ");
//        } else {
//            String bookName = dummyBookBean.getBookName();
//            Iterator<Book> iterator = BookAuthorRepo.bookListRead.iterator();
//            while (iterator.hasNext()) {
//                if (iterator.next().getBookName().equals(bookName)) {
//                    throw new BookAlreadyExistException(" Book with the author already exists");
//                }
//            }
//        }
//        String bookId = Integer.toString(BookAuthorRepo.bookListRead.size()+1);
//        Book book = new Book(bookId,dummyBookBean.getBookName(),dummyBookBean.getBookPrice(),dummyBookBean.getJournal(),BookAuthorRepo.authorList.get(dummyBookBean.bookAuthor) );
//
//      return  bookService.addBook(book);
//    }

    @PostMapping("/books")
    public Book addBooks(@RequestBody DummyBookBean dummyBookBean){
        Author author = new Author();
        System.out.println("author object "+author);

        Author[] au = new Author[BookAuthorRepo.authorList.size()];
        BookAuthorRepo.authorList.toArray(au);
        System.out.println("author array "+au.toString());

        Book[] bk = new Book[BookAuthorRepo.bookList.size()];
        BookAuthorRepo.bookList.toArray(bk);
        System.out.println("book array "+bk.toString());

        System.out.println(dummyBookBean.getBookAuthor() +" > "+ au.length);

        if (dummyBookBean.getBookAuthor() > au.length) {
            throw new AuthorNotFoundException(" author not found in the repository please add the author before adding the book ");
        } else {
            String bookName = dummyBookBean.getBookName();
            System.out.println(bookName);

            author = au[dummyBookBean.bookAuthor];
            System.out.println(author.getAuthorName());

            String authorName = au[dummyBookBean.bookAuthor].getAuthorName();
            for (int i = 0; i < bk.length; i++) {
                System.out.println(bookName +" = "+bk[i].getBookName() +" , "+ authorName+" = "+bk[i].getBookAuthor().getAuthorName());
                if (bookName.equals(bk[i].getBookName()) && authorName.equals(bk[i].getBookAuthor().getAuthorName())) {
                    throw new BookAlreadyExistException("Book with the same author exists");
                }
            }
        }
            String auId = Integer.toString(BookAuthorRepo.authorList.size()+1);
            Book book = new Book(auId ,dummyBookBean.getBookName(),dummyBookBean.getBookPrice(),dummyBookBean.getJournal(),BookAuthorRepo.authorList.get(dummyBookBean.bookAuthor)   );
           return bookService.addBook(book);
//            Iterator<Book> iterator = BookAuthorRepo.bookListRead.iterator();
//            while (iterator.hasNext()) {
//                if (iterator.next().getBookName().equals(bookName)) {
//                    throw new BookAlreadyExistException(" Book with the author already exists");
//                }
//            }



    }


    @ExceptionHandler
    public ResponseEntity<AuthorErrorResponse> handleException(AuthorNotFoundException exc){
        AuthorErrorResponse error = new AuthorErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        //return ResponseEntity
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    public ResponseEntity<BookErrorResponse> handleException(BookAlreadyExistException exc){
        //create a BookErrorResponse
        BookErrorResponse error = new BookErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        //return ResponseEntity
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
