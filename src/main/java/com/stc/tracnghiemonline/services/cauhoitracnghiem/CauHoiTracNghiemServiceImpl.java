package com.stc.tracnghiemonline.services.cauhoitracnghiem;

import com.stc.tracnghiemonline.dtos.cauhoitracnghiem.CauHoiTracNghiemDto;
import com.stc.tracnghiemonline.dtos.cauhoitracnghiem.DapAnDto;
import com.stc.tracnghiemonline.dtos.cauhoitracnghiem.ResponseCauHoiTracNghiemUserDto;
import com.stc.tracnghiemonline.entities.CauHoiTracNghiem;
import com.stc.tracnghiemonline.exceptions.InvalidException;
import com.stc.tracnghiemonline.exceptions.NotFoundException;
import com.stc.tracnghiemonline.repositories.CauHoiTracNghiemRepository;
import com.stc.tracnghiemonline.utils.PageUtils;
import com.stc.vietnamstringutils.VietnameseStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.SampleOperation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 11:56 PM
 * Filename  : CauHoiTracNghiemServiceImpl
 */
@Slf4j
@Service
public class CauHoiTracNghiemServiceImpl implements CauHoiTracNghiemService {

    private final CauHoiTracNghiemRepository cauHoiTracNghiemRepository;

    private final VietnameseStringUtils vietnameseStringUtils;

    private final MongoTemplate mongoTemplate;

    public CauHoiTracNghiemServiceImpl(CauHoiTracNghiemRepository cauHoiTracNghiemRepository,
                                       VietnameseStringUtils vietnameseStringUtils, MongoTemplate mongoTemplate) {
        this.cauHoiTracNghiemRepository = cauHoiTracNghiemRepository;
        this.vietnameseStringUtils = vietnameseStringUtils;
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public Page<CauHoiTracNghiem> getCauHoiTracNghiemPaging(String search, int page, int size, String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);
        return cauHoiTracNghiemRepository.getCauHoiTracNghiemsPaging(vietnameseStringUtils.makeSearchRegex(search), pageable);
    }

    @Override
    public Page<ResponseCauHoiTracNghiemUserDto> getCauHoiTracNghiemPagingUser(String search, int soCau, int page, int size, String sort, String column) {
        long total = cauHoiTracNghiemRepository.count();
        if (total == 0) {
            throw new InvalidException("Danh sách câu hỏi rỗng");
        }
        if (total <= 0) {
            throw new InvalidException("Số câu phải lớn hơn 0");
        }
        if (soCau > total) {
            soCau = (int) total;
        }
        SampleOperation matchStage = Aggregation.sample(soCau);
        Aggregation aggregation = Aggregation.newAggregation(matchStage);
        AggregationResults<CauHoiTracNghiem> results = mongoTemplate
                .aggregate(aggregation, "cau-hoi-trac-nghiem", CauHoiTracNghiem.class);
        List<ResponseCauHoiTracNghiemUserDto> cauHoiTracNghiems = results.getMappedResults()
                .stream().map(this::convert).collect(Collectors.toList());
        return PageUtils.convertListToPage(cauHoiTracNghiems, PageUtils.createPageable(page, size, sort, column));
    }

    @Override
    public CauHoiTracNghiem getCauHoiTracNghiem(String id) {
        return cauHoiTracNghiemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Câu hỏi có id %s không tồn tại", id)));
    }

    @Override
    public CauHoiTracNghiem getCauHoiTracNghiemCoreById(String id) {
        return cauHoiTracNghiemRepository.findById(id).orElse(null);
    }

    @Override
    public CauHoiTracNghiem createCauHoiTracNghiem(CauHoiTracNghiemDto dto) {
        if (cauHoiTracNghiemRepository.existsByCauHoiIgnoreCase(dto.getCauHoi().trim())) {
            throw new InvalidException(String.format("Câu hỏi %s đã tồn tại", dto.getCauHoi().trim()));
        }
        CauHoiTracNghiem cauHoiTracNghiem = new CauHoiTracNghiem();
        cauHoiTracNghiem.setCauHoi(dto.getCauHoi().trim());
        cauHoiTracNghiem.setDapAns(dto.getDapAns());
        cauHoiTracNghiem.setDiemSo(dto.getDiemSo());
        cauHoiTracNghiemRepository.save(cauHoiTracNghiem);
        return cauHoiTracNghiem;
    }

    @Override
    public CauHoiTracNghiem updateCauHoiTracNghiem(String id, CauHoiTracNghiemDto dto) {
        CauHoiTracNghiem cauHoiTracNghiem = getCauHoiTracNghiem(id);
        if (!cauHoiTracNghiem.getCauHoi().equals(dto.getCauHoi().trim()) &&
                !cauHoiTracNghiemRepository.existsByCauHoiIgnoreCase(dto.getCauHoi())) {
            cauHoiTracNghiem.setCauHoi(dto.getCauHoi());
        }
        cauHoiTracNghiem.setDapAns(dto.getDapAns());
        cauHoiTracNghiem.setDiemSo(dto.getDiemSo());
        cauHoiTracNghiemRepository.save(cauHoiTracNghiem);
        return cauHoiTracNghiem;
    }

    @Override
    public CauHoiTracNghiem deleteCauHoiTracNghiem(String id) {
        CauHoiTracNghiem cauHoiTracNghiem = getCauHoiTracNghiem(id);
        cauHoiTracNghiemRepository.delete(cauHoiTracNghiem);
        return cauHoiTracNghiem;
    }

    private ResponseCauHoiTracNghiemUserDto convert(CauHoiTracNghiem cauHoiTracNghiem) {
        ResponseCauHoiTracNghiemUserDto dto = new ResponseCauHoiTracNghiemUserDto();
        dto.setId(cauHoiTracNghiem.getId());
        dto.setCauHoi(cauHoiTracNghiem.getCauHoi());
        dto.setDiemSo(cauHoiTracNghiem.getDiemSo());
        List<DapAnDto> dapAns = cauHoiTracNghiem.getDapAns().stream()
                .map(dapAnEmbedded -> new DapAnDto(dapAnEmbedded.getNoiDungCauTraLoi()))
                .collect(Collectors.toList());
        dto.setDapAns(dapAns);
        return dto;
    }
}
