package com.example.homework1.service;

import com.example.homework1.event.CustomEvent;
import com.example.homework1.exception.BuyFailException;
import com.example.homework1.model.Book;
import com.example.homework1.model.BookInventory;
import com.example.homework1.request.BuyRequest;
import com.github.javafaker.Faker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookService {
    public List<Book> books = new ArrayList<>();
    public List<BookInventory> bookInventories = new ArrayList<>();
    public List<BookInventory> bookInventoryHistory = new ArrayList<>();
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    public BookService() {
        for (int i = 0; i < 1000; i++) {
            Faker faker = new Faker();
            Book book = new Book(i + "", faker.book().title(), faker.book().author());
            BookInventory bookInventory = new BookInventory(book.getId(), faker.random().nextInt(0, 100), LocalDateTime.now());
            this.books.add(book);
            this.bookInventories.add(bookInventory);
            this.bookInventoryHistory.add(bookInventory);
        }
    }

    public List<Book> getBooks() {
        Collections.sort(this.books);
        return this.books;
    }

    public List<BookInventory> getBookInventories() {
        return this.bookInventories;
    }

    public List<Book> findBooks(String keyword) {
        List<Book> bookList = this.books.stream().filter(book -> {
            return book.getTitle().toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT));
        }).collect(Collectors.toList());
        return bookList;
    }

    public List<Book> findBookByAmount(int amount) {
        List<Book> bookList = new ArrayList<>();
        Map<String, List<BookInventory>> bookInventoryMap = bookInventories.stream().collect(Collectors.groupingBy(BookInventory::getBookId));
        books.forEach(book -> {
            List<BookInventory> bookInventoryList = bookInventoryMap.get(book.getId());
            BookInventory currentBookInventory = bookInventoryList.get(bookInventoryList.size() - 1);
            if (currentBookInventory.getAmount() == amount){
                bookList.add(book);
            }
        });
        return bookList;
    }

    public List<Book> buyBook(BuyRequest buyRequest) throws BuyFailException {
        Map<String, List<BookInventory>> bookInventoryMap = bookInventories.stream().collect(Collectors.groupingBy(BookInventory::getBookId));
        List<BookInventory> bookInventoryOfBuyRequest = bookInventoryMap.get(buyRequest.getId());

        BookInventory currentBookInventory = bookInventoryOfBuyRequest.get(bookInventoryOfBuyRequest.size() - 1);
        if (currentBookInventory.getAmount() > buyRequest.getAmount()){
            int newAmount = currentBookInventory.getAmount() - buyRequest.getAmount();
            currentBookInventory.setAmount(newAmount);
            bookInventories.add(new BookInventory(currentBookInventory.getBookId(), newAmount, LocalDateTime.now()));
        }
        if (currentBookInventory.getAmount() < buyRequest.getAmount()){
            CustomEvent customEvent = new CustomEvent(buyRequest, "fail");
            applicationEventPublisher.publishEvent(customEvent);
            throw new BuyFailException("Số lượng sách trong kho không đủ");
        }
        return books.stream().filter(book -> book.getId().equals(buyRequest.getId())).collect(
            Collectors.toList());
    }

    public void checkAmountBook() {
        Map<String, List<BookInventory>> bookInventoryMap = bookInventories.stream().collect(Collectors.groupingBy(BookInventory::getBookId));
        books.forEach(book -> {
            List<BookInventory> bookInventoryList = bookInventoryMap.get(book.getId());
            BookInventory currentBookInventory = bookInventoryList.get(bookInventoryList.size() - 1);
            if (currentBookInventory.getAmount() <= 1){
                bookInventories.add(new BookInventory(book.getId(), currentBookInventory.getAmount() + 5, LocalDateTime.now()));
                System.out.printf("Da nhap them hang cho %s - %s \n",book.getId(), book.getTitle());
            }
        });
    }
}
