package com.example.homework1.schedule;

import com.example.homework1.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@ConditionalOnExpression("true ")
public class CheckInventory {

    @Autowired
    public BookService bookService;

    @Scheduled(fixedRate = 60000)
    public void run(){
        bookService.checkAmountBook();
    }

}
