package com.stc.tracnghiemonline.securities;

import com.stc.tracnghiemonline.entities.User;
import com.stc.tracnghiemonline.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 10:33 PM
 * Filename  : JwtUserDetailsService
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository taiKhoanRepository) {
        this.userRepository = taiKhoanRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Tài khoản có email %s không tồn tại", email)));
        return getUserDetails(user);
    }

    private UserDetails getUserDetails(User user) {
        return new JwtUserDetails(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()),
                user.isEnable()
        );
    }
}
