package com.stc.tracnghiemonline.entities.embedded;

import com.stc.tracnghiemonline.entities.CauHoiTracNghiem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 10:52 PM
 * Filename  : CauTraLoiEmbedded
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CauTraLoiEmbedded {
    private CauHoiTracNghiem cauHoiTracNghiem;

    private boolean traLoiDung;

    private String cauTraLoi;

    private Date thoiGianTraLoi = new Date();
}
