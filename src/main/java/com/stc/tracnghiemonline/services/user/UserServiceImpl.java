package com.stc.tracnghiemonline.services.user;

import com.stc.tracnghiemonline.dtos.user.UserDto;
import com.stc.tracnghiemonline.entities.User;
import com.stc.tracnghiemonline.exceptions.InvalidException;
import com.stc.tracnghiemonline.exceptions.NotFoundException;
import com.stc.tracnghiemonline.repositories.UserRepository;
import com.stc.tracnghiemonline.utils.EnumRole;
import com.stc.tracnghiemonline.utils.PageUtils;
import com.stc.vietnamstringutils.VietnameseStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    private final VietnameseStringUtils vietnameseStringUtils;

    @Value("${default.password}")
    private String defaultPassword;

    public UserServiceImpl(UserRepository userRepository, VietnameseStringUtils vietnameseStringUtils) {
        this.userRepository = userRepository;
        this.vietnameseStringUtils = vietnameseStringUtils;
    }

    @Override
    public Page<User> getUserPaging(String search, int page, int size, String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);
        return userRepository.getUserPaging(vietnameseStringUtils.makeSearchRegex(search), pageable);
    }

    @Override
    public User getUser(String id) {
        return userRepository.findByIdAndEnableTrue(id)
                .orElseThrow(() -> new NotFoundException(String.format("Tài khoản có id %s không tồn tại", id)));
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

    @Override
    public User createAdmin(UserDto dto) {
        if (ObjectUtils.isEmpty(dto.getEmail())) {
            throw new InvalidException("Email không được bỏ trống");
        }
        User userCoreByEmail = getUserCoreByEmail(dto.getEmail());
        if (!ObjectUtils.isEmpty(userCoreByEmail)) {
            throw new InvalidException(String.format("Tài khoản có email %s đã tồn tại", dto.getEmail()));
        }
        User user = new User();
        user.setEmail(dto.getEmail());
        if (!ObjectUtils.isEmpty(dto.getPassword())) {
            user.setPassword(dto.getPassword());
        } else {
            user.setPassword(defaultPassword);
        }
        user.setName(dto.getName());
        user.setEnable(true);
        user.setRoles(Arrays.asList(EnumRole.ROLE_ADMIN.name(), EnumRole.ROLE_THI_SINH.name()));
        userRepository.save(user);
        return user;
    }

    @Override
    public User updateUser(String id, UserDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Tài khoản có id %s không tồn tại", id)));
        user.setName(dto.getName());
        user.setPassword(dto.getPassword());
        if (!dto.getEmail().toLowerCase().trim().equals(user.getEmail()) &&
                getUserCoreByEmail(dto.getEmail().toLowerCase().trim()) == null) {
            user.setEmail(dto.getEmail().toLowerCase().trim());
        }
        user.setRoles(dto.getRoles());
        userRepository.save(user);
        return user;
    }

    @Override
    public User changeStatus(String id, Principal principal) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Tài khoản có id %s không tồn tại", id)));
        if (user.getEmail().equals(principal.getName())) {
            throw new InvalidException("Không thể thay đổi trạng thái cho chính tài khoản của mình");
        }
        user.setEnable(!user.isEnable());
        userRepository.save(user);
        return user;
    }

    @Override
    public List<String> getRoles() {
        return Arrays.stream(EnumRole.values()).map(Enum::name).collect(Collectors.toList());
    }
}
