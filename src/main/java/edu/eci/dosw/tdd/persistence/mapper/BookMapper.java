package edu.eci.dosw.tdd.persistence.mapper;

import edu.eci.dosw.tdd.controller.dto.BookDTO;
import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.persistence.entity.BookEntity;

public final class BookMapper {

    private BookMapper() {
    }

    public static Book toModel(BookEntity entity) {
        Book book = new Book();
        book.setId(entity.getId());
        book.setTitle(entity.getTitle());
        book.setAuthor(entity.getAuthor());
        book.setIsbn(entity.getIsbn());
        book.setTotalStock(entity.getTotalStock());
        book.setAvailableStock(entity.getAvailableStock());
        return book;
    }

    public static BookEntity toEntity(Book model) {
        return BookEntity.builder()
                .id(model.getId())
                .title(model.getTitle())
                .author(model.getAuthor())
                .isbn(model.getIsbn())
                .totalStock(model.getTotalStock())
                .availableStock(model.getAvailableStock())
                .build();
    }

    public static BookDTO toDto(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getTotalStock(),
                book.getAvailableStock()
        );
    }
}