package com.stc.tracnghiemonline.repositories;

import com.stc.tracnghiemonline.entities.CauHoiTracNghiem;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 11:02 PM
 * Filename  : CauHoiTracNghiemRepository
 */
public interface CauHoiTracNghiemRepository extends MongoRepository<CauHoiTracNghiem, String> {
}
