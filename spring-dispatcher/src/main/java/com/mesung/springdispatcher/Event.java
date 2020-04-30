package com.mesung.springdispatcher;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class Event {

    private String name;
    private int limitOfEnrollment;
    private LocalDateTime startDataTime;
    private LocalDateTime endDataTime;
}
