package com.msfb.cafe_finder_application.service.impl;

import com.msfb.cafe_finder_application.dto.request.UpdateUserRequest;
import com.msfb.cafe_finder_application.entity.Account;
import com.msfb.cafe_finder_application.entity.User;
import com.msfb.cafe_finder_application.repository.UserRepository;
import com.msfb.cafe_finder_application.service.AccountService;
import com.msfb.cafe_finder_application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AccountService accountService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void create(User user) {
        userRepository.insert(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User getById(String id) {
        return userRepository.findUserById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"user not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllData() {
        return userRepository.findAllUsers();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(UpdateUserRequest request) {
        User user = getById(request.getId());

        user.setName(request.getName());
        user.setNumberPhone(request.getNumberPhone());

        userRepository.update(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        User currentUser = getById(id);
        Account account = accountService.getByUserId(currentUser.getAccount().getId());
        account.setIsEnable(false);
    }
}
