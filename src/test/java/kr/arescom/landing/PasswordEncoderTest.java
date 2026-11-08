package kr.arescom.landing;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTest {

    public static void main(String[] args) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String rawPassword = "admin1234";
//        String rawPassword = "1";

        String encodedPassword = passwordEncoder.encode(rawPassword);

        System.out.println("원본 비밀번호 : " + rawPassword);
        System.out.println("암호화 비밀번호 : " + encodedPassword);

        System.out.println(
            "비밀번호 검증 : " +
            passwordEncoder.matches(rawPassword, encodedPassword)
        );
    }
}