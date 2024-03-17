package com.msfb.cafe_finder_application.service;

import com.msfb.cafe_finder_application.dto.request.UpdateUserRequest;
import com.msfb.cafe_finder_application.entity.User;

import java.util.List;

public interface UserService {
    void create(User user);
    User getById(String id);
    List<User> getAllData();
    void update(UpdateUserRequest request);
    void delete(String id);
}
