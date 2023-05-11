package com.jeanmtoure.learningjava.restapivalidation.service;

import com.jeanmtoure.learningjava.restapivalidation.dto.UserRequest;
import com.jeanmtoure.learningjava.restapivalidation.entity.User;
import com.jeanmtoure.learningjava.restapivalidation.exception.UserNotFoundException;
import com.jeanmtoure.learningjava.restapivalidation.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(UserRequest userRequest) {
        User user = User.
                build(0, userRequest.getName(), userRequest.getEmail(), userRequest.getMobile(), userRequest.getGender(), userRequest.getAge(), userRequest.getNationality());
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(int id) throws UserNotFoundException{
        User user = userRepository.findById(id);
        if (user != null) {
            return user;
        } else {
            throw new UserNotFoundException("User not found with id: "+ id);
        }
    }

    public User updateUser(int id, UserRequest userRequest) throws UserNotFoundException {
        User user = userRepository.findById(id);
        if (user != null) {
            user.setName(userRequest.getName());
            user.setEmail(userRequest.getEmail());
            user.setAge(userRequest.getAge());
            user.setMobile(userRequest.getMobile());
            user.setGender(userRequest.getGender());
            user.setNationality(userRequest.getNationality());
            return userRepository.save(user);
        } else {
            throw new UserNotFoundException("User not found with id: "+ id);
        }
    }
}
