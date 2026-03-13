package edu.eci.dosw.tdd.core.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class Book {
    private String title;
    private String author;
    private String ID;
}
