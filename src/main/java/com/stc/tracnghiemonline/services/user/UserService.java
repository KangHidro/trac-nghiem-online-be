package com.stc.tracnghiemonline.services.user;

import com.stc.tracnghiemonline.dtos.user.UserDto;
import com.stc.tracnghiemonline.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 11:14 PM
 * Filename  : UserService
 */
public interface UserService {

    Page<User> getUserPaging(String search, int page, int size, String sort, String column);

    User getUser(String id);

    User getUserCoreByEmail(String email);

    User addNewUserCore(String fullName, String email, String password);

    User createAdmin(UserDto dto);

    User updateUser(String id, UserDto dto);

    User changeStatus(String id, Principal principal);

    List<String> getRoles();
}
