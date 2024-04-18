package br.com.restapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.restapi.dtos.ApiResponseDto;
import br.com.restapi.dtos.UserDetailsRequestDto;
import br.com.restapi.exceptions.UserAlreadyExistsException;
import br.com.restapi.exceptions.UserNotFoundException;
import br.com.restapi.exceptions.UserServiceLogicException;
import br.com.restapi.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    public UserService userService;

    @PostMapping("/new")
    public ResponseEntity<ApiResponseDto<?>> registerUser(
            @Valid @RequestBody UserDetailsRequestDto userDetailsRequestDto)
            throws UserAlreadyExistsException, UserServiceLogicException {
        return userService.registerUser(userDetailsRequestDto);
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponseDto<?>> getAllUsers() throws UserServiceLogicException {
        return userService.getAllUsers();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponseDto<?>> updateUser(@Valid @RequestBody UserDetailsRequestDto userDetailsRequestDto, @PathVariable Long id)
            throws UserNotFoundException, UserServiceLogicException {
        return userService.updateUser(userDetailsRequestDto, id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDto<?>> deleteUser(@PathVariable Long id)
            throws UserNotFoundException, UserServiceLogicException {
        return userService.deleteUser(id);
    }

}
