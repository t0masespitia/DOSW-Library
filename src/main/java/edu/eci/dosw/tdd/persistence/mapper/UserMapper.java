package edu.eci.dosw.tdd.persistence.mapper;

import edu.eci.dosw.tdd.controller.dto.UserDTO;
import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.persistence.entity.UserEntity;

public final class UserMapper {

    private UserMapper() {
    }

    public static User toModel(UserEntity entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setEmail(entity.getEmail());
        user.setUsername(entity.getUsername());
        return user;
    }

    public static UserDTO toDto(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUsername()
        );
    }
}