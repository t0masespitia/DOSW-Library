package edu.eci.dosw.tdd.core.service;

import edu.eci.dosw.tdd.core.exception.UserNotFoundException;
import edu.eci.dosw.tdd.core.model.User;
import edu.eci.dosw.tdd.persistence.entity.UserEntity;
import edu.eci.dosw.tdd.persistence.mapper.UserMapper;
import edu.eci.dosw.tdd.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toModel)
                .toList();
    }

    public User getUserById(Long userId) {
        UserEntity entity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No se encontró usuario con ID: " + userId));
        return UserMapper.toModel(entity);
    }

    public UserEntity getUserEntityById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No se encontró usuario con ID: " + userId));
    }
}