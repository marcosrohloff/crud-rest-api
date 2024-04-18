package br.com.restapi.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.restapi.dtos.ApiResponseDto;
import br.com.restapi.dtos.UserDetailsRequestDto;
import br.com.restapi.exceptions.UserAlreadyExistsException;
import br.com.restapi.exceptions.UserNotFoundException;
import br.com.restapi.exceptions.UserServiceLogicException;

@Service
public interface UserService {

    ResponseEntity<ApiResponseDto<?>> registerUser(UserDetailsRequestDto newUserDetail)
            throws UserAlreadyExistsException, UserServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> getAllUsers() throws UserServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> updateUser(UserDetailsRequestDto newUserDetails, Long id)
            throws UserNotFoundException, UserServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> deleteUser(Long id)
            throws UserServiceLogicException, UserNotFoundException;

}
