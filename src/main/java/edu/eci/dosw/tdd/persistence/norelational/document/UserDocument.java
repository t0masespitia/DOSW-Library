package edu.eci.dosw.tdd.persistence.norelational.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDocument {

    @Id
    private String id;
    private String name;
    private String email;
    private String username;
    private String password;
    private String role;         // USER, BIBLIOTECARIO
    private String membershipType; // VIP, PLATINUM, STANDARD
    private LocalDate registeredDate;
}