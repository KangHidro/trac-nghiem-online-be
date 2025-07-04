package com.stc.tracnghiemonline.entities.embedded;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 10:46 PM
 * Filename  : DapAnEmbedded
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DapAnEmbedded {
    private String noiDungCauTraLoi;

    private boolean dapAnDung;
}
