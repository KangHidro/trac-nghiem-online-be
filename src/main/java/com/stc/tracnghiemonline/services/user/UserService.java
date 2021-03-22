package com.stc.tracnghiemonline.services.user;

import com.stc.tracnghiemonline.entities.User;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 11:14 PM
 * Filename  : UserService
 */
public interface UserService {

    User getUser(String email);

    User getUserCoreByEmail(String email);

    User addNewUserCore(String fullName, String email, String password);
}
