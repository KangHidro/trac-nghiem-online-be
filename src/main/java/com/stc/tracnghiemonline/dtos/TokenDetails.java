package com.stc.tracnghiemonline.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 10:26 PM
 * Filename  : TokenDetails
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenDetails {
    private String token;
    private long expired;
    private List<String> roles;
}