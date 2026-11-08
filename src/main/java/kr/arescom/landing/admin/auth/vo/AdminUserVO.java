package kr.arescom.landing.admin.auth.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUserVO {

    private String userId;
    private Integer eventId;
    private String userEnpswd;
    private String userNm;
    private String userOgdpNm;
}