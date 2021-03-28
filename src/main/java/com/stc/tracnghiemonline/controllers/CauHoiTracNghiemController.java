package com.stc.tracnghiemonline.controllers;

import com.stc.tracnghiemonline.dtos.cauhoitracnghiem.CauHoiTracNghiemDto;
import com.stc.tracnghiemonline.dtos.cauhoitracnghiem.ResponseCauHoiTracNghiemUserDto;
import com.stc.tracnghiemonline.entities.CauHoiTracNghiem;
import com.stc.tracnghiemonline.services.cauhoitracnghiem.CauHoiTracNghiemService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 11:58 PM
 * Filename  : CauHoiTracNghiemController
 */
@RestController
@RequestMapping("/rest/cau-hoi-trac-nghiem")
public class CauHoiTracNghiemController {
    private final CauHoiTracNghiemService cauHoiTracNghiemService;

    public CauHoiTracNghiemController(CauHoiTracNghiemService cauHoiTracNghiemService) {
        this.cauHoiTracNghiemService = cauHoiTracNghiemService;
    }

    /***
     * @author: thangpx
     * @param search: Từ khóa tìm kiếm câu hỏi trắc nghiệm
     * @param page: default 0
     * @param size: defalt: 20
     * @param sort: defalt asc
     * @param column: default cauHoi
     * @return: Phân trang câu hỏi trắc nghiệm
     */
    @ApiOperation(value = "Get danh sách câu hỏi phân trang cho admin")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/paging")
    public ResponseEntity<Page<CauHoiTracNghiem>> getCauHoiTracNghiemPaging(
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "page", required = false, defaultValue = "${paging.default.page}") int page,
            @RequestParam(name = "size", required = false, defaultValue = "${paging.default.size}") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "asc") String sort,
            @RequestParam(name = "column", required = false, defaultValue = "cauHoi") String column) {
        return new ResponseEntity<>(cauHoiTracNghiemService.getCauHoiTracNghiemPaging(search, page, size, sort, column), HttpStatus.OK);
    }

    /***
     * @author: thangpx
     * @param search
     * @param soCau
     * @param page
     * @param size
     * @param sort
     * @param column
     * @return: Get random 1 số câu hỏi lên cho user trả lời, default 5 câu
     */
    @ApiOperation(value = "Get danh sách câu hỏi random cho user trả lời")
    @GetMapping("/user/paging")
    public ResponseEntity<Page<ResponseCauHoiTracNghiemUserDto>> getCauHoiTracNghiemPaging(
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "soCau", required = false, defaultValue = "5") int soCau,
            @RequestParam(name = "page", required = false, defaultValue = "${paging.default.page}") int page,
            @RequestParam(name = "size", required = false, defaultValue = "${paging.default.size}") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "asc") String sort,
            @RequestParam(name = "column", required = false, defaultValue = "cauHoi") String column) {
        return new ResponseEntity<>(cauHoiTracNghiemService.getCauHoiTracNghiemPagingUser(search, soCau, page, size, sort, column),
                HttpStatus.OK);
    }


    /***
     * @author: thangpx
     * @param id
     * @return: Get câu hỏi trắc nghiệm by id
     */
    @ApiOperation(value = "Get câu hỏi trắc nghiệm by id")
    @GetMapping("/{id}")
    public ResponseEntity<CauHoiTracNghiem> getCauHoiTracNghiem(@PathVariable String id) {
        return new ResponseEntity<>(cauHoiTracNghiemService.getCauHoiTracNghiem(id), HttpStatus.OK);
    }

    /***
     * @author: thangpx
     * @param dto: DTO tạo, update câu hỏi trắc nghiệm
     * @return: Câu hỏi trắc nghiệm được tạo
     */
    @ApiOperation(value = "Admin tạo câu hỏi trắc nghiệm")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CauHoiTracNghiem> createCauHoiTracNghiem(@Valid @RequestBody CauHoiTracNghiemDto dto) {
        return new ResponseEntity<>(cauHoiTracNghiemService.createCauHoiTracNghiem(dto), HttpStatus.OK);
    }

    /***
     * @author: thangpx
     * @param id: Id câu hỏi trắc nghiệm cần update
     * @param dto: DTO tạo, update câu hỏi trắc nghiệm
     * @return: Câu hỏi trắc nghiệm được update
     */
    @ApiOperation(value = "Admin update câu hỏi trắc nghiệm")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CauHoiTracNghiem> updateCauHoiTracNghiem(@PathVariable String id,
                                                                   @Valid @RequestBody CauHoiTracNghiemDto dto) {
        return new ResponseEntity<>(cauHoiTracNghiemService.updateCauHoiTracNghiem(id, dto), HttpStatus.OK);
    }

    /***
     * @author: thangpx
     * @param id: Id câu hỏi trắc nghiệm cần delete
     * @return: Câu hỏi trắc nghiệm đã được xóa khỏi db
     */
    @ApiOperation(value = "Admin delete (xóa dưới db) câu hỏi trắc nghiệm")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<CauHoiTracNghiem> deleteCauHoiTracNghiem(@PathVariable String id) {
        return new ResponseEntity<>(cauHoiTracNghiemService.deleteCauHoiTracNghiem(id), HttpStatus.OK);
    }
}
