package com.stc.tracnghiemonline.repositories;

import com.stc.tracnghiemonline.entities.KetQuaTracNghiem;
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
 * Filename  : KetQuaTracNghiemRepository
 */
public interface KetQuaTracNghiemRepository extends MongoRepository<KetQuaTracNghiem, String> {

    @Query(value = "{'cauTraLois.cauHoiTracNghiem.cauHoi': { $regex: ?0, $options: 'i' }}", sort = "{'thoiGianTraLoi': -1}")
    Page<KetQuaTracNghiem> getKetQuaTracNghiemPaging(String search, Pageable pageable);

    @Query(value = "{'user._id': ?0}")
    Optional<KetQuaTracNghiem> getKetQuaTracNghiem(String userId);

}
