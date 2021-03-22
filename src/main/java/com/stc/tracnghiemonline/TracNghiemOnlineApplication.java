package com.stc.tracnghiemonline;

import com.stc.tracnghiemonline.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
            userService.addNewUserCore("Thắng Phạm", "thangpx@hcmute.edu.vn", "123456");
        }
        if (userService.getUserCoreByEmail("duyll@hcmute.edu.vn") == null) {
            userService.addNewUserCore("Lê Lập Duy", "duyll@hcmute.edu.vn", "123456");
        }
    }
}
