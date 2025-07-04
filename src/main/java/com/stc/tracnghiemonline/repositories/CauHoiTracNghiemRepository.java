package com.stc.tracnghiemonline.repositories;

import com.stc.tracnghiemonline.entities.CauHoiTracNghiem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 11:02 PM
 * Filename  : CauHoiTracNghiemRepository
 */
public interface CauHoiTracNghiemRepository extends MongoRepository<CauHoiTracNghiem, String> {
    @Query(value = "{'cauHoi': { $regex: ?0, $options: 'i' } }", sort = "{'cauHoi' : 1}")
    Page<CauHoiTracNghiem> getCauHoiTracNghiemsPaging(String search, Pageable pageable);

    boolean existsByCauHoiIgnoreCase(String cauHoi);

    Optional<CauHoiTracNghiem> findByCauHoiIgnoreCase(String cauHoi);

}
