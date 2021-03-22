package com.stc.tracnghiemonline.services.ketquatracnghiem;

import com.stc.tracnghiemonline.repositories.KetQuaTracNghiemRepository;
import com.stc.tracnghiemonline.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 11:51 PM
 * Filename  : KetQuaTracNghiemServiceImpl
 */
@Slf4j
@Service
public class KetQuaTracNghiemServiceImpl implements KetQuaTracNghiemService{
    private final KetQuaTracNghiemRepository ketQuaTracNghiemRepository;

    private final UserService userService;


    public KetQuaTracNghiemServiceImpl(KetQuaTracNghiemRepository ketQuaTracNghiemRepository, UserService userService) {
        this.ketQuaTracNghiemRepository = ketQuaTracNghiemRepository;
        this.userService = userService;
    }


}
