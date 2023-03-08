package com.depandre.expenseTrackerAPI.service;

import com.depandre.expenseTrackerAPI.entity.User;
import com.depandre.expenseTrackerAPI.entity.UserModel;

public interface UserService {
    public User createUser(UserModel userModel);
    public User read();

    public User update(UserModel updatedUser);

    public void delete();

    public User getLoggedInUser();


}
