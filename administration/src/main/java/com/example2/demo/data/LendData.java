package com.example2.demo.data;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LendData {

    private String title;
    private String gameId;
    private LocalDateTime lendStartDate;
    private LocalDateTime lendEndDate;
}
