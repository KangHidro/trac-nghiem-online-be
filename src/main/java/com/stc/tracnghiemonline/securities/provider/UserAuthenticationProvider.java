package com.stc.tracnghiemonline.securities.provider;

import com.stc.tracnghiemonline.securities.JwtUserDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 11:27 PM
 * Filename  : UserAuthenticationProvider
 */
public class UserAuthenticationProvider implements AuthenticationProvider {

    private final JwtUserDetailsService jwtUserDetailsService;

    public UserAuthenticationProvider(JwtUserDetailsService jwtUserDetailsService) {
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserAuthenticationToken customAuth = (UserAuthenticationToken) authentication;
        String email = customAuth.getName();
        String passWord = customAuth.getCredentials() == null ? null : customAuth.getCredentials().toString();
        boolean verifyCredentials = Boolean.parseBoolean(customAuth.isVerifyCredentials().toString());
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(email);
        if (!userDetails.isEnabled())
            throw new BadCredentialsException("Tài khoản bị khóa ");
        if (verifyCredentials) { // check username password verify;
            if (passWord.equals(userDetails.getPassword())) {
                return new UserAuthenticationToken(email, passWord, verifyCredentials, userDetails.getAuthorities()); //authenticates
            } else { // wrong Password
                throw new BadCredentialsException("Sai mật khẩu");
            }
        } else { // verify google
            return new UserAuthenticationToken(email, "N/A", verifyCredentials, userDetails.getAuthorities());
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UserAuthenticationToken.class);
    }
}
