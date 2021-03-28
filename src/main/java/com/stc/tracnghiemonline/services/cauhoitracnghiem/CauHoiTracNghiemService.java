package com.stc.tracnghiemonline.services.cauhoitracnghiem;

import com.stc.tracnghiemonline.dtos.cauhoitracnghiem.CauHoiTracNghiemDto;
import com.stc.tracnghiemonline.dtos.cauhoitracnghiem.ResponseCauHoiTracNghiemUserDto;
import com.stc.tracnghiemonline.entities.CauHoiTracNghiem;
import org.springframework.data.domain.Page;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 11:55 PM
 * Filename  : CauHoiTracNghiemService
 */
public interface CauHoiTracNghiemService {

    Page<CauHoiTracNghiem> getCauHoiTracNghiemPaging(String search, int page, int size, String sort, String column);

    Page<ResponseCauHoiTracNghiemUserDto> getCauHoiTracNghiemPagingUser(String search, int soCau, int page, int size, String sort, String column);

    CauHoiTracNghiem getCauHoiTracNghiem(String id);

    CauHoiTracNghiem getCauHoiTracNghiemCoreById(String id);

    CauHoiTracNghiem createCauHoiTracNghiem(CauHoiTracNghiemDto dto);

    CauHoiTracNghiem updateCauHoiTracNghiem(String id, CauHoiTracNghiemDto dto);

    CauHoiTracNghiem deleteCauHoiTracNghiem(String id);
}
