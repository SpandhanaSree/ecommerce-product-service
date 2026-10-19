
package com.trainingmug.ecommerce.productservice.service.auth;

import com.trainingmug.ecommerce.productservice.document.User;
import com.trainingmug.ecommerce.productservice.dto.auth.AuthResponseDto;
import com.trainingmug.ecommerce.productservice.dto.auth.LoginRequestDto;
import com.trainingmug.ecommerce.productservice.dto.auth.SignupRequestDto;
import com.trainingmug.ecommerce.productservice.dto.auth.UserResponseDto;
import com.trainingmug.ecommerce.productservice.enums.Role;
import com.trainingmug.ecommerce.productservice.repository.UserRepository;
import com.trainingmug.ecommerce.productservice.security.JwtUtil; //Generates JWT tokens
import com.trainingmug.ecommerce.productservice.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder; //Hashes passwords and checks submitted passwords
import org.springframework.stereotype.Service;

@Service //tells Spring that this class performs service-layer logic and should be managed by Spring
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository; //checks whether an email exists, finds users, and saves new users
    private final PasswordEncoder passwordEncoder; //encodes passwords before storage and verifies passwords during login
    private final JwtUtil jwtUtil; //creates access and refresh tokens
    private final ModelMapper modelMapper; //converts between request/response DTOs and the User document

    //Constructor injection
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
        if (userRepository.existsByEmail(request.getEmail())) { //checks whether a user with that email already exists
            throw new IllegalArgumentException("Email is already registered");
        }

        /*request -- contains the signup details received from the client.
          modelMapper.map() -- copies matching fields from the DTO into a new User object.
          User.class -- tells ModelMapper the target type.
        */
        User user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CUSTOMER); //Every user who signs up through this method receives the CUSTOMER role by default

        /*This sends the User object to the repository, which saves it to MongoDB and returns the saved object.
        The returned object may contain generated fields, such as its database ID
        */
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
            jwtUtil.generateAccessToken(user.getEmail(), user.getRole().name())
        );
        response.setRefreshToken(
            jwtUtil.generateRefreshToken(user.getEmail(), user.getRole().name())
        );
        response.setUser(modelMapper.map(user, UserResponseDto.class));

        return response;
    }
}
