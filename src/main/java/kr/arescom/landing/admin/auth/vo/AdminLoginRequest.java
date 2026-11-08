package kr.arescom.landing.admin.auth.vo;

public record AdminLoginRequest(
	    String adminId,
	    String adminPw
	) {
	}