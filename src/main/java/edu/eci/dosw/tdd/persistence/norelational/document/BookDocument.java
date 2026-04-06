package edu.eci.dosw.tdd.persistence.norelational.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDocument {

    @Id
    private String id;
    private String title;
    private String author;
    private String isbn;
    private LocalDate publicationDate;
    private String publicationType; // REVISTA, EBOOK, CARTILLA
    private List<String> categories;
    private Metadata metadata;
    private Availability availability;
    private LocalDate addedToCatalogDate;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Metadata {
        private Integer pages;
        private String language;
        private String publisher;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Availability {
        private String status;
        private Integer totalCopies;
        private Integer availableCopies;
        private Integer borrowedCopies;
    }
}