package kr.arescom.landing.admin.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import kr.arescom.landing.admin.auth.vo.AdminUserVO;

@Mapper
public interface AdminAuthMapper {
	
	@Select("""
		SELECT
              USER_ID 
            , EVENT_ID
            , USER_ENPSWD
            , USER_NM
            , USER_OGDP_NM
        FROM TB_LANDING_USER
        WHERE USER_ID = #{userId}
        AND EVENT_ID = (SELECT EVENT_ID FROM TB_EVENT_MANAGE WHERE USE_AT ='Y')
			""")
    AdminUserVO selectAdminUser(@Param("userId") String userId);
}
