package com.stc.tracnghiemonline.controllers;

import com.stc.tracnghiemonline.services.ketquatracnghiem.KetQuaTracNghiemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 11:59 PM
 * Filename  : KetQuaTracNghiemController
 */
@RestController
@RequestMapping("/rest/ket-qua-trac-nghiem")
public class KetQuaTracNghiemController {
    private final KetQuaTracNghiemService ketQuaTracNghiemService;

    public KetQuaTracNghiemController(KetQuaTracNghiemService ketQuaTracNghiemService) {
        this.ketQuaTracNghiemService = ketQuaTracNghiemService;
    }
}
