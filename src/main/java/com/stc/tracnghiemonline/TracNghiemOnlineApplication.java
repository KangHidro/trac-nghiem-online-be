package com.stc.tracnghiemonline;

import com.stc.tracnghiemonline.dtos.user.UserDto;
import com.stc.tracnghiemonline.entities.User;
import com.stc.tracnghiemonline.repositories.UserRepository;
import com.stc.tracnghiemonline.services.user.UserService;
import com.stc.tracnghiemonline.utils.EnumRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Optional;

@SpringBootApplication
public class TracNghiemOnlineApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(TracNghiemOnlineApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (userService.getUserCoreByEmail("thangpx@hcmute.edu.vn") == null) {
            userService.createAdmin(new UserDto("thangpx@hcmute.edu.vn", "123456", "Thắng Phạm",
                    Arrays.asList(EnumRole.ROLE_ADMIN.name(), EnumRole.ROLE_THI_SINH.name())));
        }
    }
}
