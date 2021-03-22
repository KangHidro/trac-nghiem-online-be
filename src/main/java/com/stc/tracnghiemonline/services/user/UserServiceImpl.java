package com.stc.tracnghiemonline.services.user;

import com.stc.tracnghiemonline.entities.User;
import com.stc.tracnghiemonline.exceptions.NotFoundException;
import com.stc.tracnghiemonline.repositories.UserRepository;
import com.stc.tracnghiemonline.utils.EnumRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 11:14 PM
 * Filename  : UserServiceImpl
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(String.format("", email)));
    }

    @Override
    public User getUserCoreByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User addNewUserCore(String fullName, String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setName(fullName);
        user.setPassword(password);
        user.setRoles(Collections.singletonList(EnumRole.ROLE_THI_SINH.name()));
        user.setEnable(true);
        userRepository.save(user);
        return user;
    }
}
