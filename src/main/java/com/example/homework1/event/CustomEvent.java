package com.example.homework1.event;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class CustomEvent extends ApplicationEvent {
    @Override
    public String toString() {
        return "CustomEvent [message=" + message + ", source = " + this.source + "]";
    }
    private String message;

    public CustomEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public CustomEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
