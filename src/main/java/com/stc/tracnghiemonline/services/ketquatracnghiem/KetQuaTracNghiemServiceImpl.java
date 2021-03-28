package com.stc.tracnghiemonline.services.ketquatracnghiem;

import com.stc.tracnghiemonline.dtos.ketquatracnghiem.KetQuaTracNghiemDto;
import com.stc.tracnghiemonline.entities.CauHoiTracNghiem;
import com.stc.tracnghiemonline.entities.KetQuaTracNghiem;
import com.stc.tracnghiemonline.entities.User;
import com.stc.tracnghiemonline.entities.embedded.DapAnEmbedded;
import com.stc.tracnghiemonline.exceptions.InvalidException;
import com.stc.tracnghiemonline.exceptions.NotFoundException;
import com.stc.tracnghiemonline.repositories.KetQuaTracNghiemRepository;
import com.stc.tracnghiemonline.services.cauhoitracnghiem.CauHoiTracNghiemService;
import com.stc.tracnghiemonline.services.user.UserService;
import com.stc.tracnghiemonline.utils.PageUtils;
import com.stc.vietnamstringutils.VietnameseStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 11:51 PM
 * Filename  : KetQuaTracNghiemServiceImpl
 */
@Slf4j
@Service
public class KetQuaTracNghiemServiceImpl implements KetQuaTracNghiemService {
    private final KetQuaTracNghiemRepository ketQuaTracNghiemRepository;

    private final UserService userService;

    private final VietnameseStringUtils vietnameseStringUtils;

    private final CauHoiTracNghiemService cauHoiTracNghiemService;


    public KetQuaTracNghiemServiceImpl(KetQuaTracNghiemRepository ketQuaTracNghiemRepository,
                                       UserService userService, VietnameseStringUtils vietnameseStringUtils,
                                       CauHoiTracNghiemService cauHoiTracNghiemService) {
        this.ketQuaTracNghiemRepository = ketQuaTracNghiemRepository;
        this.userService = userService;
        this.vietnameseStringUtils = vietnameseStringUtils;
        this.cauHoiTracNghiemService = cauHoiTracNghiemService;
    }

    @Override
    public Page<KetQuaTracNghiem> getKetQuaTracNghiemPaging(String search, int page, int size, String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);
        return ketQuaTracNghiemRepository.getKetQuaTracNghiemPaging(vietnameseStringUtils.makeSearchRegex(search), pageable);
    }

    @Override
    public KetQuaTracNghiem submitKetQua(KetQuaTracNghiemDto dto, String email) {
        User user = userService.getUserCoreByEmail(email);
        if (user == null || !user.isEnable()) {
            throw new InvalidException(String.format("Tài khoản có email %s không tồn tại", email));
        }
        KetQuaTracNghiem ketQuaTracNghiemOld = getKetQuaTracNghiem(email);
        KetQuaTracNghiem ketQuaTracNghiem = new KetQuaTracNghiem();
        ketQuaTracNghiem.setUser(user);
        ketQuaTracNghiem.setCauTraLois(dto.getCauTraLois());
        AtomicInteger soCauTraLoiDung = new AtomicInteger();
        AtomicReference<Double> tongDiem = new AtomicReference<>((double) 0);
        // Tính số câu trả lời đúng
        dto.getCauTraLois().forEach(cauTraLoiEmbedded -> {
            CauHoiTracNghiem cauHoiTracNghiem = cauHoiTracNghiemService.getCauHoiTracNghiem(cauTraLoiEmbedded.getCauHoiTracNghiem().getId());
            List<DapAnEmbedded> dapAns = cauHoiTracNghiem.getDapAns().stream().filter(DapAnEmbedded::isDapAnDung).collect(Collectors.toList());
            if (dapAns.size() != 1) {
                throw new InvalidException(String.format("Câu hỏi %s không hợp lệ", cauHoiTracNghiem.getCauHoi()));
            }
            if (dapAns.get(0).getNoiDungCauTraLoi().equalsIgnoreCase(cauTraLoiEmbedded.getCauTraLoi().getNoiDungCauTraLoi())) {
                soCauTraLoiDung.getAndIncrement();
                tongDiem.updateAndGet(v -> v + cauHoiTracNghiem.getDiemSo());
            }
        });
        ketQuaTracNghiem.setSoCauTraLoiDung(soCauTraLoiDung.get());
        ketQuaTracNghiem.setTongDiem(tongDiem.get());
        ketQuaTracNghiemRepository.save(ketQuaTracNghiem);

        //TODO: Xóa kết quả trắc nghiệm cũ
        if (ketQuaTracNghiemOld != null)
            ketQuaTracNghiemRepository.delete(ketQuaTracNghiemOld);
        return ketQuaTracNghiem;
    }

    @Override
    public KetQuaTracNghiem getKetQuaTracNghiem(String email) {
        User user = userService.getUserCoreByEmail(email);
        if (user == null || !user.isEnable()) {
            throw new InvalidException(String.format("Tài khoản có email %s không tồn tại", email));
        }
        return ketQuaTracNghiemRepository.getKetQuaTracNghiem(user.getId())
                .orElse(null);
    }

    @Override
    public KetQuaTracNghiem deleteKetQuaTracNghiem(String id) {
        KetQuaTracNghiem ketQuaTracNghiem = ketQuaTracNghiemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Kết quả trắc nghiệm có id %s không tồn tại", id)));
        ketQuaTracNghiemRepository.delete(ketQuaTracNghiem);
        return ketQuaTracNghiem;
    }
}
