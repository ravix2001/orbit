package com.ravi.orbit.service;

import com.ravi.orbit.dto.UserDto;
import com.ravi.orbit.entity.Cart;
import com.ravi.orbit.entity.Seller;
import com.ravi.orbit.entity.User;
import com.ravi.orbit.enums.USER_ROLE;
import com.ravi.orbit.exceptions.UserAlreadyExistsException;
import com.ravi.orbit.repository.CartRepository;
import com.ravi.orbit.repository.SellerRepository;
import com.ravi.orbit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final SellerRepository sellerRepository;

    private final CartRepository cartRepository;

    private final PasswordEncoder passwordEncoder;

//    // Not required if we use @RequiredArgsConstructor
//    // Constructor Injection
//    public UserService(UserRepository userRepository, CartRepository cartRepository) {
//        this.userRepository = userRepository;
//        this.cartRepository = cartRepository;
//    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public User saveNewUser(UserDto userDto) throws Exception{
        String username = userDto.getUsername();
        User userWithUsername = userRepository.findByUsername(username);
        Seller sellerWithUsername = sellerRepository.findByUsername(username);
        if (userWithUsername != null || sellerWithUsername != null){
            throw new UserAlreadyExistsException("User with username " + username + " already exists");
        }

        String email = userDto.getEmail();
        User userWithEmail = userRepository.findByEmail(email);
        Seller sellerWithEmail = sellerRepository.findByEmail(email);
        if (userWithEmail != null ||  sellerWithEmail != null){
            throw new UserAlreadyExistsException("User with email " + email + " already exists");
        }

        // Save user to the database
        User savedUser = userRepository.save(mapToUserEntity(userDto));

        // Initialize and save a cart for the user
        Cart newCart = new Cart();
        newCart.setUser(savedUser);
        newCart.setCartItems(Set.of());
        cartRepository.save(newCart);

        return savedUser;
    }

    public User saveExistingUser(String username, UserDto userDto){
        return userRepository.save(mapToExistingUserEntity(username, userDto));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public boolean deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    public User mapToUserEntity (UserDto userDto) {
        User newUser = new User();
        newUser.setFullName(userDto.getFullName());
        newUser.setEmail(userDto.getEmail());
        newUser.setPhone(userDto.getPhone());
        newUser.setGender(userDto.getGender());
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
//        newUser.setRole(USER_ROLE.CUSTOMER);
        newUser.setDeliveryAddress(userDto.getDeliveryAddress());

        return newUser;
    }

    public User mapToExistingUserEntity (String username, UserDto userDto) {
        User existingUser = userRepository.findByUsername(username);

        // Update only non-null fields provided in the DTO
        if (userDto.getFullName() != null) {
            existingUser.setFullName(userDto.getFullName());
        }
        if (userDto.getEmail() != null) {
            existingUser.setEmail(userDto.getEmail());
        }
        if (userDto.getPhone() != null) {
            existingUser.setPhone(userDto.getPhone());
        }
        if (userDto.getGender() != null) {
            existingUser.setGender(userDto.getGender());
        }
        if (userDto.getUsername() != null) {
            existingUser.setUsername(userDto.getUsername());
        }
        if (userDto.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        if (userDto.getDeliveryAddress() != null) {
            existingUser.setDeliveryAddress(userDto.getDeliveryAddress());
        }

        return existingUser;
    }

}
