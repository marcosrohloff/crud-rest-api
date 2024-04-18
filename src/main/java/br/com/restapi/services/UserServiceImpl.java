package br.com.restapi.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import br.com.restapi.dtos.ApiResponseDto;
import br.com.restapi.dtos.ApiResponseStatus;
import br.com.restapi.dtos.UserDetailsRequestDto;
import br.com.restapi.entity.User;
import br.com.restapi.exceptions.UserAlreadyExistsException;
import br.com.restapi.exceptions.UserNotFoundException;
import br.com.restapi.exceptions.UserServiceLogicException;
import br.com.restapi.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<ApiResponseDto<?>> registerUser(UserDetailsRequestDto newUserDetails)
            throws UserAlreadyExistsException, UserServiceLogicException {

        try {
            if (userRepository.findByEmail(newUserDetails.getEmail()) != null) {
                throw new UserAlreadyExistsException(
                        "Registration failed: User already exists with email " + newUserDetails.getEmail());
            }
            if (userRepository.findByUsername(newUserDetails.getUserName()) != null) {
                throw new UserAlreadyExistsException(
                        "Registration failed: User already exists with username " + newUserDetails.getUserName());
            }

            User newUser = new User(newUserDetails.getUserName(), newUserDetails.getEmail(), newUserDetails.getPhone(),
                    LocalDateTime.now());
            userRepository.save(newUser);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto<>(ApiResponseStatus.SUCCESS.name(),
                    "New user account has been successfully created!"));

        } catch (UserAlreadyExistsException e) {
            throw new UserAlreadyExistsException(e.getMessage());
        } catch (Exception e) {
            log.error("Failed to create new user account: " + e.getMessage());
            throw new UserServiceLogicException();
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> getAllUsers() throws UserServiceLogicException {

        try {
            List<User> users = userRepository.findAllByOrderByRegDateTimeDesc();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseDto<>(ApiResponseStatus.SUCCESS.name(), users));
        } catch (Exception e) {
            log.error("Failed to fetch all users: " + e.getMessage());
            throw new UserServiceLogicException();
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> updateUser(UserDetailsRequestDto newUserDetails, Long id)
            throws UserNotFoundException, UserServiceLogicException {
        try {

            User user = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
            user.setEmail(newUserDetails.getEmail());
            user.setUsername(newUserDetails.getUserName());
            user.setPhone(newUserDetails.getPhone());

            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseDto<>(ApiResponseStatus.SUCCESS.name(), "User account updated successfully!"));

        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        } catch (Exception e) {
            log.error("Failed to update user account: " + e.getMessage());
            throw new UserServiceLogicException();
        }

    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> deleteUser(Long id)
            throws UserServiceLogicException, UserNotFoundException {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
            userRepository.delete(user);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseDto<>(ApiResponseStatus.SUCCESS.name(), "User account deleted successfully!"));

        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        } catch (Exception e) {
            log.error("Failed to delete user account: " + e.getMessage());
            throw new UserServiceLogicException();
        }
    }

}
