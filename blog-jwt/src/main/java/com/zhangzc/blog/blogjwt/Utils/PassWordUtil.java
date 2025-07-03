package com.zhangzc.blog.blogjwt.Utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PassWordUtil {
    private final PasswordEncoder passwordEncoder;

    public String EncodePassword(String passWord) {
        return passwordEncoder.encode(passWord);
    }

}
