package edu.eci.dosw.tdd.config;

import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.core.repository.BookRepositoryPort;
import edu.eci.dosw.tdd.core.repository.UserRepositoryPort;
import edu.eci.dosw.tdd.core.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final BookRepositoryPort bookRepository;
    private final UserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (bookRepository.findAll().isEmpty()) {
            Book book1 = new Book();
            book1.setTitle("Clean Code");
            book1.setAuthor("Robert C. Martin");
            book1.setIsbn("9780132350884");
            book1.setTotalStock(5);
            book1.setAvailableStock(5);
            bookRepository.save(book1);

            Book book2 = new Book();
            book2.setTitle("Domain-Driven Design");
            book2.setAuthor("Eric Evans");
            book2.setIsbn("9780321125217");
            book2.setTotalStock(3);
            book2.setAvailableStock(3);
            bookRepository.save(book2);
        }

        if (userRepository.findAll().isEmpty()) {
            User user1 = new User();
            user1.setName("Juan");
            user1.setEmail("juan@email.com");
            user1.setUsername("juan");
            user1.setPassword(passwordEncoder.encode("123456"));
            user1.setRole("USER");
            userRepository.save(user1);

            User user2 = new User();
            user2.setName("Admin");
            user2.setEmail("admin@email.com");
            user2.setUsername("admin");
            user2.setPassword(passwordEncoder.encode("admin123"));
            user2.setRole("BIBLIOTECARIO");
            userRepository.save(user2);
        }
    }
}