package com.stc.tracnghiemonline.controllers;

import com.stc.tracnghiemonline.dtos.ketquatracnghiem.KetQuaTracNghiemDto;
import com.stc.tracnghiemonline.entities.KetQuaTracNghiem;
import com.stc.tracnghiemonline.services.ketquatracnghiem.KetQuaTracNghiemService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 11:59 PM
 * Filename  : KetQuaTracNghiemController
 */
@RestController
@RequestMapping("/rest/ket-qua-trac-nghiem")
public class KetQuaTracNghiemController {
    private final KetQuaTracNghiemService ketQuaTracNghiemService;

    public KetQuaTracNghiemController(KetQuaTracNghiemService ketQuaTracNghiemService) {
        this.ketQuaTracNghiemService = ketQuaTracNghiemService;
    }

    /***
     * @author: thangpx
     * @param search
     * @param page
     * @param size
     * @param sort
     * @param column
     * @return: Danh sách kết quả trắc nghiệm phân trang cho admin
     */
    @ApiOperation(value = "Get danh sách câu trả lời phân trang cho admin")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/paging")
    public ResponseEntity<Page<KetQuaTracNghiem>> getCauHoiTracNghiemPaging(
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "page", required = false, defaultValue = "${paging.default.page}") int page,
            @RequestParam(name = "size", required = false, defaultValue = "${paging.default.size}") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "asc") String sort,
            @RequestParam(name = "column", required = false, defaultValue = "thoiGianTraLoi") String column) {
        return new ResponseEntity<>(ketQuaTracNghiemService.getKetQuaTracNghiemPaging(search, page, size, sort, column), HttpStatus.OK);
    }

    /***
     * @author: thangpx
     * @param dto: DTO submit kết quả trắc nghiệm
     * @param principal: token user trả lời
     * @return: Kết quả trắc nghiệm của user, 1 user chỉ có 1 kết quả trắc nghiệm mới nhất
     */
    @ApiOperation(value = "User submit câu hỏi trắc nghiệm, xóa bỏ submit trước đó")
    @PostMapping
    public ResponseEntity<KetQuaTracNghiem> submitKetQua(@Valid @RequestBody KetQuaTracNghiemDto dto, Principal principal) {
        return new ResponseEntity<>(ketQuaTracNghiemService.submitKetQua(dto, principal.getName()), HttpStatus.OK);
    }

    /***
     * @author: thangpx
     * @param email: email user cần get kết quả trắc nghiệm
     * @return: Kết quả trắc nghiệm của user
     */
    @ApiOperation(value = "Get kết quả trắc nghiệm by email")
    @GetMapping("/{email:.+}")
    public ResponseEntity<KetQuaTracNghiem> getKetQuaTracNghiem(@PathVariable String email) {
        return new ResponseEntity<>(ketQuaTracNghiemService.getKetQuaTracNghiem(email), HttpStatus.OK);
    }

    /***
     * @author: thangpx
     * @param id: Id kết quả trắc nghiệm cần delete
     * @return: Kết quả trắc nghiệm đã được xóa khỏi db
     */
    @ApiOperation(value = "Admin delete kết quả trắc nghiệm (xóa dưới db)")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<KetQuaTracNghiem> deleteKetQuaTracNghiem(@PathVariable String id) {
        return new ResponseEntity<>(ketQuaTracNghiemService.deleteKetQuaTracNghiem(id), HttpStatus.OK);
    }
}
