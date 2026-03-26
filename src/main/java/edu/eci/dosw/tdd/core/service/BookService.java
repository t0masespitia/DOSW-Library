package edu.eci.dosw.tdd.core.service;

import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.persistence.entity.BookEntity;
import edu.eci.dosw.tdd.persistence.mapper.BookMapper;
import edu.eci.dosw.tdd.persistence.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookMapper::toModel)
                .toList();
    }

    public Book getBookById(Long bookId) {
        BookEntity entity = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró libro con ID: " + bookId));
        return BookMapper.toModel(entity);
    }

    public BookEntity getBookEntityById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró libro con ID: " + bookId));
    }

    public Book saveBook(Book book) {
        BookEntity saved = bookRepository.save(BookMapper.toEntity(book));
        return BookMapper.toModel(saved);
    }

    public void decrementInventory(BookEntity book) {
        if (book.getAvailableStock() <= 0) {
            throw new IllegalArgumentException("No hay stock disponible para el libro con ID: " + book.getId());
        }
        book.setAvailableStock(book.getAvailableStock() - 1);
        bookRepository.save(book);
    }

    public void incrementInventory(BookEntity book) {
        if (book.getAvailableStock() < book.getTotalStock()) {
            book.setAvailableStock(book.getAvailableStock() + 1);
            bookRepository.save(book);
        }
    }
}