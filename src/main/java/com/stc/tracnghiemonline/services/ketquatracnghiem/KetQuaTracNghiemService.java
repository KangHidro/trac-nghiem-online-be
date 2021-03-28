package com.stc.tracnghiemonline.services.ketquatracnghiem;

import com.stc.tracnghiemonline.dtos.ketquatracnghiem.KetQuaTracNghiemDto;
import com.stc.tracnghiemonline.entities.KetQuaTracNghiem;
import org.springframework.data.domain.Page;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 11:51 PM
 * Filename  : KetQuaTracNghiemService
 */
public interface KetQuaTracNghiemService {

    Page<KetQuaTracNghiem> getKetQuaTracNghiemPaging(String search, int page, int size, String sort, String column);

    KetQuaTracNghiem submitKetQua(KetQuaTracNghiemDto dto, String email);

    KetQuaTracNghiem getKetQuaTracNghiem(String email);

    KetQuaTracNghiem deleteKetQuaTracNghiem(String id);
}
