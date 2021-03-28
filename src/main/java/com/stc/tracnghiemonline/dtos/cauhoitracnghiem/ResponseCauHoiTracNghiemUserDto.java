package com.stc.tracnghiemonline.dtos.cauhoitracnghiem;

import com.stc.tracnghiemonline.entities.embedded.DapAnEmbedded;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/28/21
 * Time      : 10:52 AM
 * Filename  : ResponseCauHoiTracNghiemUserDto
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCauHoiTracNghiemUserDto {
    private String id;

    private String cauHoi;

    private List<DapAnDto> dapAns = new ArrayList<>();

    private double diemSo = 0.0;
}
