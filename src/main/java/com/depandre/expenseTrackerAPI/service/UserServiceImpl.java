package com.depandre.expenseTrackerAPI.service;

import com.depandre.expenseTrackerAPI.entity.User;
import com.depandre.expenseTrackerAPI.entity.UserModel;
import com.depandre.expenseTrackerAPI.excpetions.ItemExistsAlreadyException;
import com.depandre.expenseTrackerAPI.excpetions.ResourceNotFoundException;
import com.depandre.expenseTrackerAPI.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public User createUser(UserModel userModel) {
        if(userRepository.existsByEmail(userModel.getEmail())){
            throw new ItemExistsAlreadyException("User is already registered with email " + userModel.getEmail());
        }
        User newUser = new User();
        BeanUtils.copyProperties(userModel, newUser);
        newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    public User read() throws ResourceNotFoundException {
        Long userId = getLoggedInUser().getId();
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) return user.get();
        else throw new ResourceNotFoundException("User is not found for id " + userId);
    }

    @Override
    public User update(UserModel updatedUser)  throws ResourceNotFoundException{
        User currUser = read();
        if(updatedUser.getName() != null) currUser.setName(updatedUser.getName());
        if(updatedUser.getEmail() != null) {
            /* We have to check if updated Email is not with in any id */
            if(userRepository.existsByEmail(updatedUser.getEmail())){
                throw new ItemExistsAlreadyException("User is already registered with email " + updatedUser.getEmail());
            }
            currUser.setEmail(updatedUser.getEmail());
        }
        if(updatedUser.getPassword() != null) currUser.setPassword(bcryptEncoder.encode(updatedUser.getPassword()));
        if(updatedUser.getAge() != null) currUser.setAge(updatedUser.getAge());
        return userRepository.save(currUser);
    }

    @Override
    public void delete() {
        User currUser = read();
        userRepository.delete(currUser); /* OR userRepository.deleteById(id) */
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            return user.get();
        }
        else throw new UsernameNotFoundException("User is not found for this email " + email);
    }
}
