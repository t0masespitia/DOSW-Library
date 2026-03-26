package edu.eci.dosw.tdd.config;

import edu.eci.dosw.tdd.persistence.entity.BookEntity;
import edu.eci.dosw.tdd.persistence.entity.Role;
import edu.eci.dosw.tdd.persistence.entity.UserEntity;
import edu.eci.dosw.tdd.persistence.repository.BookRepository;
import edu.eci.dosw.tdd.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) {

        if (bookRepository.count() == 0) {
            BookEntity book1 = BookEntity.builder()
                    .title("Clean Code")
                    .author("Robert C. Martin")
                    .isbn("9780132350884")
                    .totalStock(5)
                    .availableStock(5)
                    .build();

            BookEntity book2 = BookEntity.builder()
                    .title("Domain-Driven Design")
                    .author("Eric Evans")
                    .isbn("9780321125217")
                    .totalStock(3)
                    .availableStock(3)
                    .build();

            bookRepository.save(book1);
            bookRepository.save(book2);
        }

        if (userRepository.count() == 0) {
            UserEntity user1 = UserEntity.builder()
                    .name("Juan")
                    .email("juan@email.com")
                    .username("juan")
                    .password("123456")
                    .role(Role.USER)
                    .build();

            UserEntity user2 = UserEntity.builder()
                    .name("Admin")
                    .email("admin@email.com")
                    .username("admin")
                    .password("admin123")
                    .role(Role.BIBLIOTECARIO)
                    .build();

            userRepository.save(user1);
            userRepository.save(user2);
        }
    }
}