package edu.eci.dosw.tdd.core.service;

import edu.eci.dosw.tdd.controller.dto.BookDTO;
import edu.eci.dosw.tdd.core.model.Book;
import edu.eci.dosw.tdd.core.repository.BookRepositoryPort;
import edu.eci.dosw.tdd.persistence.relational.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepositoryPort bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró libro con ID: " + bookId));
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public BookDTO createBook(BookDTO dto) {
        Book book = new Book();
        book.setTitle(dto.title());
        book.setAuthor(dto.author());
        book.setIsbn(dto.isbn());
        book.setTotalStock(dto.totalStock());
        book.setAvailableStock(dto.availableStock());
        Book saved = bookRepository.save(book);
        return BookMapper.toDto(saved);
    }

    public BookDTO updateBook(Long id, BookDTO dto) {
        Book book = getBookById(id);
        book.setTitle(dto.title());
        book.setAuthor(dto.author());
        book.setIsbn(dto.isbn());
        book.setTotalStock(dto.totalStock());
        book.setAvailableStock(dto.availableStock());
        return BookMapper.toDto(bookRepository.save(book));
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new IllegalArgumentException("Libro no encontrado con ID: " + id);
        }
        bookRepository.deleteById(id);
    }

    public void decrementStock(Book book) {
        if (book.getAvailableStock() <= 0) {
            throw new IllegalArgumentException("No hay stock disponible para el libro con ID: " + book.getId());
        }
        book.setAvailableStock(book.getAvailableStock() - 1);
        bookRepository.save(book);
    }

    public void incrementStock(Book book) {
        if (book.getAvailableStock() < book.getTotalStock()) {
            book.setAvailableStock(book.getAvailableStock() + 1);
            bookRepository.save(book);
        }
    }

    public Book getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró libro con ISBN: " + isbn));
    }

}