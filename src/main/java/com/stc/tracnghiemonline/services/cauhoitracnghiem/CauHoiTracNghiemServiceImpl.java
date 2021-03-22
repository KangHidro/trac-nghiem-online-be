package com.stc.tracnghiemonline.services.cauhoitracnghiem;

import com.stc.tracnghiemonline.repositories.CauHoiTracNghiemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 11:56 PM
 * Filename  : CauHoiTracNghiemServiceImpl
 */
@Slf4j
@Service
public class CauHoiTracNghiemServiceImpl implements CauHoiTracNghiemService {
    private final CauHoiTracNghiemRepository cauHoiTracNghiemRepository;

    public CauHoiTracNghiemServiceImpl(CauHoiTracNghiemRepository cauHoiTracNghiemRepository) {
        this.cauHoiTracNghiemRepository = cauHoiTracNghiemRepository;
    }
}
