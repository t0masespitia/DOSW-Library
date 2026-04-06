package edu.eci.dosw.tdd.persistence.norelational.repository;

import edu.eci.dosw.tdd.persistence.norelational.document.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoUserRepository extends MongoRepository<UserDocument, String> {
    Optional<UserDocument> findByUsername(String username);
    Optional<UserDocument> findByEmail(String email);
}