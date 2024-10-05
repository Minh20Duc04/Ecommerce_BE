package com.Ecommerce_BE.Service.ServiceImp;

import com.Ecommerce_BE.Dto.LoginRequest;
import com.Ecommerce_BE.Dto.Response;
import com.Ecommerce_BE.Dto.UserDto;
import com.Ecommerce_BE.Enum.UserRole;
import com.Ecommerce_BE.Exception.InvalidCredentialsException;
import com.Ecommerce_BE.Exception.NotFoundException;
import com.Ecommerce_BE.Mapper.EntityDtoMapper;
import com.Ecommerce_BE.Model.User;
import com.Ecommerce_BE.Repository.UserRepository;
import com.Ecommerce_BE.Security.JwtUtils;
import com.Ecommerce_BE.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final EntityDtoMapper entityDtoMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public Response registerUser(UserDto registrationRequest) {
        //neu k cung cap quyen thi mac dinh nguoi dung co quyen User
        UserRole role = UserRole.USER;

        if(registrationRequest.getRole() != null && registrationRequest.getRole().equalsIgnoreCase("admin"))
        {
            role = UserRole.ADMIN;
        }
        User user = User.builder()
                .name(registrationRequest.getName())
                .email(registrationRequest.getEmail())
                .phoneNumber(registrationRequest.getPhoneNumber())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .role(role)
                .build();
        User savedUser = userRepository.save(user);
        System.out.println(savedUser);

        UserDto userDto = entityDtoMapper.mapUserToDtoBasic(savedUser);

        return Response.builder()
                .status(200)
                .message("User successfully added")
                .user(userDto)
                .build();

    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new NotFoundException("Email not found"));
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
        {
            throw new InvalidCredentialsException("Password does not match");
        }
        String token = jwtUtils.generateToken(user);

        return Response.builder()
                .status(200)
                .message("User successfully logged In")
                .token(token)
                .expirationTime("6 months")
                .role(user.getRole().name())
                .build();
    }

    @Override
    public Response getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream()
                .map(entityDtoMapper::mapUserToDtoBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .userList(userDtos)
                .build();
    }

    @Override
    public User getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        log.info("User email is: " + email);
        return userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not found"));
    }

    @Override
    public Response getUserInfoAndOrderHistory() {
        User user = getLoginUser();
        UserDto userDto = entityDtoMapper.mapUserToDtoPlusAddressAndOrderHistory(user);

        return Response.builder()
                .status(200)
                .user(userDto)
                .build();
    }
}
