package com.example.homework1.controller;

import com.example.homework1.annotation.Benchmark;
import com.example.homework1.event.CustomEvent;
import com.example.homework1.exception.BuyFailException;
import com.example.homework1.model.Book;
import com.example.homework1.model.BookInventory;
import com.example.homework1.request.BuyRequest;
import com.example.homework1.service.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    @Benchmark
    @GetMapping("")
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false) String search, Integer amount){
        if (search != null){
            return new ResponseEntity<>(bookService.findBooks(search), HttpStatus.OK);
        } else if (amount != null){
            return new ResponseEntity<>(bookService.findBookByAmount(amount), HttpStatus.OK);
        }
        return new ResponseEntity<>(bookService.getBooks(), HttpStatus.OK);
    }

    @Benchmark
    @PostMapping("")
    public ResponseEntity<List<Book>> buyBook(@RequestBody BuyRequest buyRequest) throws BuyFailException {
        System.out.println("Customer buy a book");
        CustomEvent customEvent = new CustomEvent(buyRequest, "BUY");
        applicationEventPublisher.publishEvent(customEvent);
        return new ResponseEntity<>(bookService.buyBook(buyRequest), HttpStatus.OK);
    }
}
