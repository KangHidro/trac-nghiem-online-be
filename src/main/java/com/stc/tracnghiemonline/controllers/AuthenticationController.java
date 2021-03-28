package com.stc.tracnghiemonline.controllers;

import com.stc.tracnghiemonline.dtos.AccountDto;
import com.stc.tracnghiemonline.dtos.TokenDetails;
import com.stc.tracnghiemonline.dtos.user.UserDto;
import com.stc.tracnghiemonline.entities.User;
import com.stc.tracnghiemonline.exceptions.InvalidException;
import com.stc.tracnghiemonline.exceptions.UserNotFoundAuthenticationException;
import com.stc.tracnghiemonline.securities.JwtTokenUtils;
import com.stc.tracnghiemonline.securities.JwtUserDetailsService;
import com.stc.tracnghiemonline.securities.provider.UserAuthenticationToken;
import com.stc.tracnghiemonline.services.user.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 11:10 PM
 * Filename  : AuthenticationController
 */
@RestController
@RequestMapping("/rest")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailsService taiKhoanDetailsService;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private UserService userService;

    @Value("${google.verifyUrl}")
    private String googleVerifyUrl;

    @Value("${default.password}")
    private String defaultPassword;


    RestTemplate restTemplate = new RestTemplate();

    /***
     * @author: thangpx
     * @param dto: Dto login bằng tài khoản mật khẩu
     * @return: token detail user login thành công
     */
    @ApiOperation(value = "Login username, password")
    @PostMapping("/login")
    public ResponseEntity<TokenDetails> login(@RequestBody AccountDto dto) {
        UserAuthenticationToken authenticationToken = new UserAuthenticationToken(
                dto.getUsername(),
                dto.getPassword(),
                true
        );
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (UserNotFoundAuthenticationException | BadCredentialsException ex) {
            throw new InvalidException(ex.getMessage());
        }
        final UserDetails userDetails = taiKhoanDetailsService
                .loadUserByUsername(dto.getUsername());
        final TokenDetails result = jwtTokenUtils.getTokenDetails(userDetails);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /***
     * @author: thangpx
     * @param googleToken: access token google
     * @return: token detail của user login thành công, nếu user chưa có tài khoản thì tạo luôn tài khoản cho user bằng email google
     */
    @ApiOperation(value = "Login google")
    @PostMapping("/login/google")
    public ResponseEntity<TokenDetails> loginGoogle(@RequestHeader(name = "GoogleToken") String googleToken) {
        String urlRequest = googleVerifyUrl + googleToken;
        String email;
        String fullName;
        try {
            ResponseEntity<HashMap> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, null, HashMap.class);
            HashMap<String, String> map = responseEntity.getBody();
            email = map.get("email");
        } catch (Exception ex) {
            throw new InvalidException("Token is Invalid");
        }
        UserAuthenticationToken authenticationToken = new UserAuthenticationToken(email, null, false);
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (UserNotFoundAuthenticationException ex) {
            userService.addNewUserCore(null, email, defaultPassword);
        } catch (BadCredentialsException ex) {
            throw new InvalidException(ex.getMessage());
        }
        final UserDetails userDetails = taiKhoanDetailsService.loadUserByUsername(email);
        final TokenDetails result = jwtTokenUtils.getTokenDetails(userDetails);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /***
     * @author: thangpx
     * @param search: Từ khóa tìm kiếm theo email hoặc name
     * @param page: Số trang, bắt đầu từ 0
     * @param size: số phần tử trên 1 trang, default 20
     * @param sort: chưa dùng tới, sort trên mongo
     * @param column: ko dùng tới, sort trên mongo
     * @return
     */
    @ApiOperation(value = "Get danh sách tài khoản phân trang cho admin")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/paging")
    public ResponseEntity<Page<User>> getUserPaging(
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "page", required = false, defaultValue = "${paging.default.page}") int page,
            @RequestParam(name = "size", required = false, defaultValue = "${paging.default.size}") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "asc") String sort,
            @RequestParam(name = "column", required = false, defaultValue = "email") String column) {
        return new ResponseEntity<>(userService.getUserPaging(search, page, size, sort, column), HttpStatus.OK);
    }

    /***
     * @author: thangpx
     * @param dto: Dto tạo tài khoản admin mới
     * @return: Tài khoản admin mới được tạo
     */
    @ApiOperation(value = "Create new admin")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/user")
    public ResponseEntity<User> createAdmin(@Valid @RequestBody UserDto dto) {
        return new ResponseEntity<>(userService.createAdmin(dto), HttpStatus.OK);
    }

    /***
     * @author: thangpx
     * @param principal: token tài khoản cần lấy thông tin
     * @return: Tài khoản của user hiện tại
     */
    @ApiOperation(value = "Get thông tin tài khoản by token")
    @GetMapping("/user/")
    public ResponseEntity<User> getUser(Principal principal) {
        return new ResponseEntity<>(userService.getUser(principal), HttpStatus.OK);
    }

    /***
     * @author: thangpx
     * @param id: ID tài khoản cần cập nhật thông tin
     * @param dto: Dto cập nhật thông tin tài khoản (name, email, password)
     * @return: Tài khoản đã được cập nhật thông tin thành công
     */
    @ApiOperation(value = "Admin cập nhật thông tin tài khoản (name, email, password)")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @Valid @RequestBody UserDto dto) {
        return new ResponseEntity<>(userService.updateUser(id, dto), HttpStatus.OK);
    }

    /***
     * @author: thangpx
     * @param id: Id tài khoản cần thay đổi trạng thái kích hoạt
     * @return
     */
    @ApiOperation(value = "Admin thay đổi trạng thái kích hoạt tài khoản user")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<User> changeStatus(@PathVariable String id, Principal principal) {
        return new ResponseEntity<>(userService.changeStatus(id, principal), HttpStatus.OK);
    }

    /***
     * @author: thangpx
     * @param principal
     * @return: Test login
     */
    @ApiOperation(value = "Test security")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hello")
    public ResponseEntity<String> sayHello(Principal principal) {
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }

    /***
     * @author: thangpx
     * @return: Get danh sách role từ enum role
     */
    @ApiOperation(value = "Get danh sách role từ enum role")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/role")
    public ResponseEntity<List<String>> getRoles() {
        return new ResponseEntity<>(userService.getRoles(), HttpStatus.OK);
    }

    /***
     * @author: thangpx
     * @param principal: token user
     * @param dto
     * @return: Cập nhật tên cho user, trả về user đã được cập nhật thông tin do login google ko có name
     */
    @ApiOperation(value = "User cập nhật thông tin tài khoản (name)")
    @PutMapping("/user/name")
    public ResponseEntity<User> updateName(@Valid @RequestBody UserDto dto, Principal principal) {
        return new ResponseEntity<>(userService.updateName(dto, principal), HttpStatus.OK);
    }
}
