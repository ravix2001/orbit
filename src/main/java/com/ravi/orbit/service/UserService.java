package com.ravi.orbit.service;

import com.ravi.orbit.dto.UserDto;
import com.ravi.orbit.entity.Cart;
import com.ravi.orbit.entity.User;
import com.ravi.orbit.repository.CartRepository;
import com.ravi.orbit.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final CartRepository cartRepository;

    // Constructor Injection
    public UserService(UserRepository userRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public User setUser(UserDto userDto){


        User savedUser = userRepository.save(mapToUserEntity(userDto));

        // Initialize cart for the new user if not present
        cartRepository.findByUserId(savedUser.getId()).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(savedUser);
            newCart.setCartItems(Set.of());
            return cartRepository.save(newCart);
        });

        return savedUser;
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

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User mapToUserEntity (UserDto userDto) {
        User newUser = new User();
        newUser.setFullName(userDto.getFullName());
        newUser.setEmail(userDto.getEmail());
        newUser.setPhone(userDto.getPhone());
        newUser.setGender(userDto.getGender());
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(userDto.getPassword());
        newUser.setDeliveryAddress(userDto.getDeliveryAddress());

        return newUser;
    }

}
