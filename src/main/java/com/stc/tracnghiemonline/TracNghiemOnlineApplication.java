package com.stc.tracnghiemonline;

import com.stc.tracnghiemonline.dtos.user.UserDto;
import com.stc.tracnghiemonline.entities.CauHoiTracNghiem;
import com.stc.tracnghiemonline.entities.KetQuaTracNghiem;
import com.stc.tracnghiemonline.entities.User;
import com.stc.tracnghiemonline.entities.embedded.CauTraLoiEmbedded;
import com.stc.tracnghiemonline.exceptions.NotFoundException;
import com.stc.tracnghiemonline.repositories.CauHoiTracNghiemRepository;
import com.stc.tracnghiemonline.repositories.KetQuaTracNghiemRepository;
import com.stc.tracnghiemonline.services.cauhoitracnghiem.CauHoiTracNghiemService;
import com.stc.tracnghiemonline.services.user.UserService;
import com.stc.tracnghiemonline.utils.EnumRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class TracNghiemOnlineApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private CauHoiTracNghiemService cauHoiTracNghiemService;

    @Autowired
    private CauHoiTracNghiemRepository cauHoiTracNghiemRepository;

    @Autowired
    private KetQuaTracNghiemRepository ketQuaTracNghiemRepository;

    public static void main(String[] args) {
        SpringApplication.run(TracNghiemOnlineApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (userService.getUserCoreByEmail("thangphamspk@gmail.com") == null) {
            userService.createAdmin(new UserDto("thangphamspk@gmail.com", "123456", "Thắng Phạm",
                    Arrays.asList(EnumRole.ROLE_THI_SINH.name())));
        }
//        User user = userService.getUserCoreByEmail("thangphamspk@gmail.com");
//        List<CauHoiTracNghiem> cauHoiTracNghiems = cauHoiTracNghiemRepository.findAll();
//        KetQuaTracNghiem ketQuaTracNghiem = new KetQuaTracNghiem();
//        List<CauTraLoiEmbedded> cauTraLoiEmbeddeds = cauHoiTracNghiems.stream().map(cauHoiTracNghiem -> new CauTraLoiEmbedded(cauHoiTracNghiem, true,
//                cauHoiTracNghiem.getDapAns().get(0))).collect(Collectors.toList());
//        ketQuaTracNghiem.setSoCauTraLoiDung(3);
//        ketQuaTracNghiem.setTongDiem(0);
//        ketQuaTracNghiem.setUser(user);
//        ketQuaTracNghiem.setCauTraLois(cauTraLoiEmbeddeds);
//        ketQuaTracNghiemRepository.save(ketQuaTracNghiem);
//        KetQuaTracNghiem ketQuaTracNghiem = ketQuaTracNghiemRepository.getKetQuaTracNghiem(user.getId())
//                .orElseThrow(() -> new NotFoundException(String.format("Kết quả trắc nghiệm của user %s không tồn tại", user.getEmail())));
//
        System.out.println("DONE");
    }
}
