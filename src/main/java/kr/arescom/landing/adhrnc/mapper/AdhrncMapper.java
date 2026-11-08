package kr.arescom.landing.adhrnc.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import kr.arescom.landing.adhrnc.vo.AdhrncVO;

@Mapper
public interface AdhrncMapper {

	@Insert("""
	    INSERT INTO TB_LANDING_ADHRNC (
			EVENT_ID
	        , ADHRNC_NM
	        , MOBLPHON
	        , EMAIL
	        , CUST_NM_01
	        , CUST_NM_02
	        , CUST_NM_03
	        , CUST_NM_04
	        , CUST_NM_05
	        , CUST_NM_06
	        , CUST_NM_07
	        , CUST_NM_08
	        , CUST_NM_09
	        , CUST_NM_10
	        , CUST_NM_11
	        , CUST_NM_12
	        , CUST_NM_13
	        , CUST_NM_14
	        , CUST_NM_15
	        , USE_AT
	        , RGTR_ID
	        , REG_DT
	    ) VALUES (
			  (SELECT EVENT_ID FROM TB_EVENT_MANAGE WHERE USE_AT = 'Y')
	        , #{adhrncNm}
	        , #{moblphon}
	        , #{email}
	        , #{custNm01}
	        , #{custNm02}
	        , #{custNm03}
	        , #{custNm04}
	        , #{custNm05}
	        , #{custNm06}
	        , #{custNm07}
	        , #{custNm08}
	        , #{custNm09}
	        , #{custNm10}
	        , #{custNm11}
	        , #{custNm12}
	        , #{custNm13}
	        , #{custNm14}
	        , #{custNm15}
	        , 'Y'
	        , 'LANDING'
	        , NOW()
	    )
	    """)
	int insertAdhrnc(AdhrncVO vo);

}
