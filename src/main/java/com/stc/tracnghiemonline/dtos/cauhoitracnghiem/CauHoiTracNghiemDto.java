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
 * Date      : 3/23/21
 * Time      : 12:20 AM
 * Filename  : CauHoiTracNghiemDto
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CauHoiTracNghiemDto {
    private String cauHoi;

    private List<DapAnEmbedded> dapAns = new ArrayList<>();

    private double diemSo = 0.0;
}
