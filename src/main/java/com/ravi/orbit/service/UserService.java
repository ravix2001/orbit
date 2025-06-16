package com.ravi.orbit.service;

import com.ravi.orbit.dto.UserDto;
import com.ravi.orbit.entity.Address;
import com.ravi.orbit.entity.User;
import com.ravi.orbit.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

//      // Dependency Injection
//    @Autowired
//    private UserRepository userRepository;

    public final UserRepository userRepository;

    // Constructor Injection
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public User setUser(UserDto userDto){
        User newUser = new User();
        newUser.setFullName(userDto.getFullName());
        newUser.setEmail(userDto.getEmail());
        newUser.setPhone(userDto.getPhone());
        newUser.setGender(userDto.getGender());
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(userDto.getPassword());
        newUser.setDeliveryAddress(userDto.getDeliveryAddress());

//        newUser.setAddresses(userDto.getAddresses().stream().map(addressDto -> {
//            Address address = new Address();
//            address.setName(addressDto.getName());
//            address.setLocality(addressDto.getLocality());
//            address.setCity(addressDto.getCity());
//            address.setState(addressDto.getState());
//            address.setCountry(addressDto.getCountry());
//            address.setZipcode(addressDto.getZipcode());
//            address.setPhone(addressDto.getPhone());
//            address.setUser(newUser);
//            return address;
//        }).collect(Collectors.toSet()));

        return userRepository.save(newUser);
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

}
