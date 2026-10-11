
package com.trainingmug.ecommerce.productservice.service.auth;

import com.trainingmug.ecommerce.productservice.document.User;
import com.trainingmug.ecommerce.productservice.dto.auth.AuthResponseDto;
import com.trainingmug.ecommerce.productservice.dto.auth.LoginRequestDto;
import com.trainingmug.ecommerce.productservice.dto.auth.SignupRequestDto;
import com.trainingmug.ecommerce.productservice.dto.auth.UserResponseDto;
import com.trainingmug.ecommerce.productservice.enums.Role;
import com.trainingmug.ecommerce.productservice.repository.UserRepository;
import com.trainingmug.ecommerce.productservice.security.JwtUtil;
import com.trainingmug.ecommerce.productservice.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil,
            ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserResponseDto signup(SignupRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        User user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CUSTOMER);

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponseDto.class);
    }

    @Override
    public AuthResponseDto login(LoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        AuthResponseDto response = new AuthResponseDto();
        response.setAccessToken(
                jwtUtil.generateAccessToken(user.getEmail(), user.getRole().name()));
        response.setRefreshToken(
                jwtUtil.generateRefreshToken(user.getEmail(), user.getRole().name()));
        response.setUser(modelMapper.map(user, UserResponseDto.class));

        return response;
    }
}
