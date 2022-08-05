package com.example.restController.controller;

import com.example.restController.configuration.EndPointHitRate;
import com.example.restController.entity.Author;
import com.example.restController.entity.Book;
import com.example.restController.repository.BookAuthorRepo;
import com.example.restController.service.AuthorService;
import com.example.restController.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.listener.AuditListener;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    BookService bookService;
    @Autowired
    AuthorService authorService;
//    @Autowired
//    CustomTraceRepository customTraceRepository;
    @Autowired
    RestTemplate restTemplate;

    AuditResult auditResult;

    List<EndPointHitRate> resultentList = new ArrayList<>();

    @GetMapping("/greet")
    public String greetPeople(){
        return "good morning";
    }

    //Using restTemplate to call external Api's
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

//    @GetMapping("/auditing")
//    public List<EndPointHitRate> getAudit(){
//       // RestTemplate restTemplate = new RestTemplate();
//       // List<HttpTrace>  traceRecord = restTemplate.getForObject("http://localhost:8089/actuator/httptrace",HttpTrace.class);
//
//       List<String> list =  customTraceRepository.findAll().stream().map(ep-> ep.getRequest().getUri().toString()).distinct().collect(Collectors.toList());
//
//        resultentList.removeAll(resultentList);
//
//        for (String list1 : list) {
//            EndPointHitRate endPointHitRate = new EndPointHitRate();
//            endPointHitRate.setEndPoint(list1);
//            long successRate = customTraceRepository.findAll().stream().filter(ep-> ep.getRequest().getUri().toString().equals(list1)).filter(e->e.getResponse().getStatus()==200).count();
//            long failureRate = customTraceRepository.findAll().stream().filter(ep-> ep.getRequest().getUri().toString().equals(list1)).filter(e->e.getResponse().getStatus()!=200).count();
//            endPointHitRate.setSuccess((int) successRate);
//            endPointHitRate.setFailure((int) failureRate);
//            resultentList.add(endPointHitRate);
//            endPointHitRate = null;
//        }
//      return resultentList;
//    }


//    @GetMapping("/audit")
//    public List<EndPointHitRate> getEndpointMetrics(){
//        ObjectMapper  objectMapper = new ObjectMapper();
//        RestTemplate restTemplate = new RestTemplate();
//
//        List<EndPointHitRate>  httpTrace =  restTemplate.getForObject("http://localhost:8089/api/auditing",List.class);
////     HttpTrace trace = objectMapper.readValue(httpTrace,HttpTrace.class);
// //       HttpTrace trace = restTemplate.getForObject("http://localhost:8089/actuator/httptrace",HttpTrace.class);
//        ResponseEntity<HttpTrace> traceEndpoint = restTemplate.getForEntity("http://localhost:8089/actuator/httptrace",HttpTrace.class  );
//        System.out.println(traceEndpoint.getStatusCodeValue());
//        System.out.println(traceEndpoint.getHeaders().get("uri"));
//       return httpTrace;
//    }
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
//    @PostMapping("/books")
//    public Book addBook(@RequestBody DummyBookBean dummyBookBean){
//        Author author = null;
//        boolean bookExists = false;
//        boolean authorExists = false;
//        if (dummyBookBean.getBookAuthor() > authorService.getAuthors().size()) {
//            throw new AuthorNotFoundException(" author not found in the repository please add the author before adding the book ");
//        }else{
//        for (Book book : bookService.getBooks()) {
//            if (book.getBookName().equals(dummyBookBean.getBookName())) {
//                bookExists = true;
//                System.out.println(book.getBookName() +" = "+ dummyBookBean.getBookName());
//                if (book.getBookAuthor().equals(authorService.getAuthorById(dummyBookBean.getBookAuthor()-1))) {
//                    authorExists = true;
//                }
//            }
//        }
//
//        if (bookExists == false) {
//            for (Author author1 : authorService.getAuthors()) {
//                if (authorService.getAuthorById(dummyBookBean.getBookAuthor()-1).getAuthorName().equals(author1.getAuthorName())) {
//                    authorExists = true;
//                    author = author1;
//                    break;
//                }
//            }
//        }
//        if (bookExists && authorExists) {
//            throw new BookAlreadyExistException(" Book with the author already exists");
//       }
//        else {
//            Book book = new Book(String.valueOf(bookService.getBooks().size()+1),dummyBookBean.getBookName(),dummyBookBean.getBookPrice(),dummyBookBean.getJournal(),author);
//            bookService.addBook(book);
//            return book;
//        }}
//    }

    @PostMapping("/books")
    public Book addBooks(@RequestBody DummyBookBean dummyBookBean){
        Boolean bookAndAuthorExists = false;
        Boolean authorExists = false;
        System.out.println(dummyBookBean.getBookAuthor()+" ,  "+BookAuthorRepo.bookList.get(BookAuthorRepo.bookList.size()-1).getBookId());
        if (dummyBookBean.getBookAuthor() > Integer.parseInt(BookAuthorRepo.bookList.get(BookAuthorRepo.bookList.size()-1).getBookId()+1)) {
            throw new AuthorNotFoundException(" author not found in the repository please add the author before adding the book ");
        } else {
            System.out.println();
            bookAndAuthorExists = BookAuthorRepo.bookList.stream().anyMatch(book -> book.getBookAuthor().getAuthorName().equals(BookAuthorRepo.authorList.get(dummyBookBean.bookAuthor-1).getAuthorName()) && book.getBookName().equals(dummyBookBean.bookName));
        }
        if (bookAndAuthorExists) {
            throw new BookAlreadyExistException(" Book with the author already exists");
        } else {
            Book book = new  Book(Integer.toString(BookAuthorRepo.bookList.size()+1),dummyBookBean.getBookName(),dummyBookBean.getBookPrice(),dummyBookBean.getJournal(),BookAuthorRepo.authorList.get(dummyBookBean.getBookAuthor()-1));
            bookService.addBook(book);
            return book;
        }
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
