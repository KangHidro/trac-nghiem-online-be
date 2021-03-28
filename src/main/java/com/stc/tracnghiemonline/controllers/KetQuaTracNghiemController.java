package com.stc.tracnghiemonline.controllers;

import com.stc.tracnghiemonline.dtos.ketquatracnghiem.KetQuaTracNghiemDto;
import com.stc.tracnghiemonline.entities.KetQuaTracNghiem;
import com.stc.tracnghiemonline.services.ketquatracnghiem.KetQuaTracNghiemService;
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

    @PostMapping
    public ResponseEntity<KetQuaTracNghiem> submitKetQua(@Valid @RequestBody KetQuaTracNghiemDto dto, Principal principal) {
        return new ResponseEntity<>(ketQuaTracNghiemService.submitKetQua(dto, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/{{email:.+}}")
    public ResponseEntity<KetQuaTracNghiem> getKetQuaTracNghiem(@PathVariable String email) {
        return new ResponseEntity<>(ketQuaTracNghiemService.getKetQuaTracNghiem(email), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<KetQuaTracNghiem> deleteKetQuaTracNghiem(@PathVariable String id) {
        return new ResponseEntity<>(ketQuaTracNghiemService.deleteKetQuaTracNghiem(id), HttpStatus.OK);
    }
}
