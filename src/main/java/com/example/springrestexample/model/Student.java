package com.example.springrestexample.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Student {
    private String id;
    private String name;
    private String address;
    private LocalDate dateOfBirth;
    private String standard;
    private int rollNumber;

}
