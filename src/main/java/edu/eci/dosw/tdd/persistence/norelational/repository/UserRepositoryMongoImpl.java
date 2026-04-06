package edu.eci.dosw.tdd.persistence.norelational.repository;

import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.core.repository.UserRepositoryPort;
import edu.eci.dosw.tdd.persistence.norelational.document.UserDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("mongo")
@RequiredArgsConstructor
public class UserRepositoryMongoImpl implements UserRepositoryPort {

    private final MongoUserRepository mongoUserRepository;

    @Override
    public User save(User user) {
        UserDocument doc = UserDocument.builder()
                .id(user.getId() != null ? user.getId().toString() : null)
                .name(user.getName())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .registeredDate(LocalDate.now())
                .build();
        return toModel(mongoUserRepository.save(doc));
    }

    @Override
    public Optional<User> findById(Long id) {
        return mongoUserRepository.findById(id.toString()).map(this::toModel);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return mongoUserRepository.findByUsername(username).map(this::toModel);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return mongoUserRepository.findByEmail(email).map(this::toModel);
    }

    @Override
    public List<User> findAll() {
        return mongoUserRepository.findAll().stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        mongoUserRepository.deleteById(id.toString());
    }

    @Override
    public boolean existsById(Long id) {
        return mongoUserRepository.existsById(id.toString());
    }

    private User toModel(UserDocument doc) {
        User user = new User();
        user.setId(null); // MongoDB usa ObjectId, no Long
        user.setName(doc.getName());
        user.setEmail(doc.getEmail());
        user.setUsername(doc.getUsername());
        user.setPassword(doc.getPassword());
        user.setRole(doc.getRole());
        return user;
    }
}