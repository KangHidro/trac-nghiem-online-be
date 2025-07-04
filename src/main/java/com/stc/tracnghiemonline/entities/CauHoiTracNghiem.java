package com.stc.tracnghiemonline.entities;

import com.stc.tracnghiemonline.entities.embedded.DapAnEmbedded;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 10:44 PM
 * Filename  : CauHoiTracNghiem
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cau-hoi-trac-nghiem")
public class CauHoiTracNghiem {
    @Id
    private String id;

    @ApiModelProperty(value = "Nội dung câu hỏi")
    private String cauHoi;

    private List<DapAnEmbedded> dapAns = new ArrayList<>();

    private double diemSo = 0.0;
}
