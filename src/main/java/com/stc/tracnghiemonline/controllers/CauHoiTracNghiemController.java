package com.stc.tracnghiemonline.controllers;

import com.stc.tracnghiemonline.services.cauhoitracnghiem.CauHoiTracNghiemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 11:58 PM
 * Filename  : CauHoiTracNghiemController
 */
@RestController
@RequestMapping("/rest/cau-hoi-trac-nghiem")
public class CauHoiTracNghiemController {
    private final CauHoiTracNghiemService cauHoiTracNghiemService;

    public CauHoiTracNghiemController(CauHoiTracNghiemService cauHoiTracNghiemService) {
        this.cauHoiTracNghiemService = cauHoiTracNghiemService;
    }
}
