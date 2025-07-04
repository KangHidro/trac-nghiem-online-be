package com.stc.tracnghiemonline;

import com.stc.tracnghiemonline.entities.User;
import com.stc.tracnghiemonline.repositories.UserRepository;
import com.stc.tracnghiemonline.utils.EnumRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.util.Arrays;
import java.util.stream.Collectors;

@EnableEurekaClient
@SpringBootApplication
public class TracNghiemOnlineApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(TracNghiemOnlineApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.count()==0){
            User user = new User();
            user.setEmail("duyll@hcmute.edu.vn");
            user.setName("Kang Hidro");
            user.setRoles(Arrays.stream(EnumRole.values()).map(Enum::name).collect(Collectors.toList()));
            userRepository.save(user);
        }
    }
}
