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
        // simulate user creating logic
        log.info("creating User: {}", input);

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
