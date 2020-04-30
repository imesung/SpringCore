package com.mesung.springdispatcher;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {
    public List<Event> getEvents() {
        Event event1 = Event.builder()
                .name("스프링 웹 MVC 1차")
                .limitOfEnrollment(5)
                .startDataTime(LocalDateTime.of(2020, 1, 1, 12, 10))
                .endDataTime(LocalDateTime.of(2020, 1, 1, 12, 20))
                .build();

        Event event2 = Event.builder()
                .name("스프링 웹 MVC 2차")
                .limitOfEnrollment(5)
                .startDataTime(LocalDateTime.of(2020, 1, 1, 12, 10))
                .endDataTime(LocalDateTime.of(2020, 1, 1, 12, 20))
                .build();

        return List.of(event1, event2);
    }
}
