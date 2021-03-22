package com.stc.tracnghiemonline.controllers;

import com.stc.tracnghiemonline.entities.CauHoiTracNghiem;
import com.stc.tracnghiemonline.services.cauhoitracnghiem.CauHoiTracNghiemService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/user/paging")
    public ResponseEntity<Page<CauHoiTracNghiem>> getCauHoiTracNghiemPaging(
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "soCau", required = false, defaultValue = "5") int soCau,
            @RequestParam(name = "page", required = false, defaultValue = "${paging.default.page}") int page,
            @RequestParam(name = "size", required = false, defaultValue = "${paging.default.size}") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "asc") String sort,
            @RequestParam(name = "column", required = false, defaultValue = "cauHoi") String column) {
        return new ResponseEntity<>(cauHoiTracNghiemService.getCauHoiTracNghiemPagingUser(search, soCau, page, size, sort, column),
                HttpStatus.OK);
    }

}
