package com.depandre.expenseTrackerAPI.service;

import com.depandre.expenseTrackerAPI.entity.User;
import com.depandre.expenseTrackerAPI.entity.UserModel;
import com.depandre.expenseTrackerAPI.excpetions.ItemExistsAlreadyException;
import com.depandre.expenseTrackerAPI.excpetions.ResourceNotFoundException;
import com.depandre.expenseTrackerAPI.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(UserModel userModel) {
        if(userRepository.existsByEmail(userModel.getEmail())){
            throw new ItemExistsAlreadyException("User is already registered with email " + userModel.getEmail());
        }
        User newUser = new User();
        BeanUtils.copyProperties(userModel, newUser);
        return userRepository.save(newUser);
    }

    @Override
    public User read(Long id) throws ResourceNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) return user.get();
        else throw new ResourceNotFoundException("User is not found for id " + id);
    }

    @Override
    public User update(UserModel updatedUser, Long id)  throws ResourceNotFoundException{
        User currUser = read(id);
        if(updatedUser.getName() != null) currUser.setName(updatedUser.getName());
        if(updatedUser.getEmail() != null) {
            /* We have to check if updated Email is not with in any id */
            if(userRepository.existsByEmail(updatedUser.getEmail())){
                throw new ItemExistsAlreadyException("User is already registered with email " + updatedUser.getEmail());
            }
            currUser.setEmail(updatedUser.getEmail());
        }
        if(updatedUser.getPassword() != null) currUser.setPassword(updatedUser.getPassword());
        if(updatedUser.getAge() != null) currUser.setAge(updatedUser.getAge());
        return userRepository.save(currUser);
    }

    @Override
    public void delete(Long id) {
        User currUser = read(id);
        userRepository.delete(currUser); /* OR userRepository.deleteById(id) */
    }
}
