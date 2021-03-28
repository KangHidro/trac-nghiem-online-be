package com.stc.tracnghiemonline.entities;

import com.stc.tracnghiemonline.entities.embedded.CauTraLoiEmbedded;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 10:51 PM
 * Filename  : KetQuaTracNghiem
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ket-qua-trac-nghiem")
public class KetQuaTracNghiem {
    @Id
    private String id;

    @DBRef
    private User user;

    private List<CauTraLoiEmbedded> cauTraLois = new ArrayList<>();

    private int soCauTraLoiDung;

    private double tongDiem;

    private Date thoiGianTraLoi = new Date();
}
