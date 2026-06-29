package com.leetjourney.user_service.service;

import com.leetjourney.user_service.dto.UserDto;
import com.leetjourney.user_service.entity.User;
import com.leetjourney.user_service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto createUser(UserDto input) {

        final User createUser = User.builder()
                .name(input.getName())
                .surname(input.getSurname())
                .email(input.getEmail())
                .address(input.getAddress())
                .alerting(input.isAlerting())
                .energyAlertingThreshold(input.getEnergyAlertingThreshold())
                .build();

        final User savedUser = userRepository.save(createUser);

        return toDto(savedUser);
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

    public void updateUser(Long id, UserDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new IllegalStateException("User with id " + id + " not found"));

        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setAddress(dto.getAddress());
        user.setAlerting(dto.isAlerting());
        user.setEnergyAlertingThreshold(dto.getEnergyAlertingThreshold());

        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                        .orElseThrow(()-> new IllegalArgumentException("User with id " + id + " not found"));

        userRepository.delete(user);
    }

    private UserDto toDto(User user) {
        return UserDto.builder().
                id(user.getId()).
                name(user.getName()).
                surname(user.getSurname()).
                email(user.getEmail()).
                address(user.getAddress()).
                alerting(user.isAlerting()).
                energyAlertingThreshold(user.getEnergyAlertingThreshold()).
                build();
    }
}
