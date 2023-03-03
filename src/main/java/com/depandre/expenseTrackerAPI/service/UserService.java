package com.depandre.expenseTrackerAPI.service;

import com.depandre.expenseTrackerAPI.entity.User;
import com.depandre.expenseTrackerAPI.entity.UserModel;

public interface UserService {
    public User createUser(UserModel userModel);
    public User read(Long id);

    public User update(UserModel updatedUser, Long id);

    public void delete(Long id);


}
