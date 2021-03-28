package com.stc.tracnghiemonline.dtos.ketquatracnghiem;

import com.stc.tracnghiemonline.entities.embedded.CauTraLoiEmbedded;
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
 * Time      : 2:29 AM
 * Filename  : KetQuaTracNghiemDto
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KetQuaTracNghiemDto {
    private List<CauTraLoiEmbedded> cauTraLois = new ArrayList<>();
}
