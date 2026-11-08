package kr.arescom.landing.admin.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.arescom.landing.admin.auth.mapper.AdminAuthMapper;
import kr.arescom.landing.admin.auth.vo.AdminLoginRequest;
import kr.arescom.landing.admin.auth.vo.AdminLoginResponse;
import kr.arescom.landing.admin.auth.vo.AdminUserVO;
import kr.arescom.landing.core.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminAuthService {

    private final AdminAuthMapper adminAuthMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public AdminLoginResponse login(AdminLoginRequest request) {

        // 관리자 계정 조회
        AdminUserVO adminUser = adminAuthMapper.selectAdminUser(request.adminId());

        // 계정이 없는 경우
        if (adminUser == null) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.adminPw(),adminUser.getUserEnpswd())) {
            throw new IllegalArgumentException(
                "아이디 또는 비밀번호가 올바르지 않습니다."
            );
        }

        // JWT 발급
        String accessToken = jwtTokenProvider.generateToken(adminUser.getUserId());

        return new AdminLoginResponse(accessToken);
    }
}