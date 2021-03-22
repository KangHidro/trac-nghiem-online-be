package com.stc.tracnghiemonline.controllers;

import com.stc.tracnghiemonline.dtos.AccountDto;
import com.stc.tracnghiemonline.dtos.TokenDetails;
import com.stc.tracnghiemonline.exceptions.InvalidException;
import com.stc.tracnghiemonline.exceptions.UserNotFoundAuthenticationException;
import com.stc.tracnghiemonline.securities.JwtTokenUtils;
import com.stc.tracnghiemonline.securities.JwtUserDetailsService;
import com.stc.tracnghiemonline.securities.provider.UserAuthenticationToken;
import com.stc.tracnghiemonline.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.HashMap;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 11:10 PM
 * Filename  : AuthenticationController
 */
@RestController
@RequestMapping("/rest/login")
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

    @PostMapping
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

    @PostMapping("/google")
    public ResponseEntity<TokenDetails> loginGoogle(@RequestHeader(name = "GoogleToken") String googleToken) {
        String urlRequest = googleVerifyUrl + googleToken;
        String email;
        String fullName;
        try {
            ResponseEntity<HashMap> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, null, HashMap.class);
            HashMap<String, String> map = responseEntity.getBody();
            email = map.get("email");
            fullName = map.get("full_name");
        } catch (Exception ex) {
            throw new InvalidException("Token is Invalid");
        }
        UserAuthenticationToken authenticationToken = new UserAuthenticationToken(email, null, false);
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (UserNotFoundAuthenticationException ex) {
            userService.addNewUserCore(fullName, email, defaultPassword);
        } catch (BadCredentialsException ex) {
            throw new InvalidException(ex.getMessage());
        }
        final UserDetails userDetails = taiKhoanDetailsService.loadUserByUsername(email);
        final TokenDetails result = jwtTokenUtils.getTokenDetails(userDetails);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/hello")
    public ResponseEntity<String> sayHello(Principal principal) {
        return new ResponseEntity<>(principal.getName(), HttpStatus.OK);
    }
}
