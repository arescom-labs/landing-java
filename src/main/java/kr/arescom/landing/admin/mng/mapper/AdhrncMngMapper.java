package kr.arescom.landing.admin.mng.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import kr.arescom.landing.adhrnc.vo.AdhrncVO;
import kr.arescom.landing.admin.mng.vo.AdhrncMngSearchVO;
import kr.arescom.landing.admin.mng.vo.AdhrncMngSummaryVO;

@Mapper
public interface AdhrncMngMapper {

    @SelectProvider(
        type = AdhrncMngProvider.class,
        method = "selectAdhrncMngList"
    )
    List<AdhrncVO> selectAdhrncMngList(AdhrncMngSearchVO searchVO);

    @SelectProvider(
        type = AdhrncMngProvider.class,
        method = "selectAdhrncMngCount"
    )
    int selectAdhrncMngCount(AdhrncMngSearchVO searchVO);
    
    @SelectProvider(
    	    type = AdhrncMngProvider.class,
    	    method = "selectAdhrncMngExcelList"
    	)
    	List<Map<String, Object>> selectAdhrncMngExcelList(
    	    @Param("searchVO") AdhrncMngSearchVO searchVO,
    	    @Param("columns") List<String> columns
    	);

    @Select("""
		SELECT
		    COUNT(*) AS totalCount,
		    SUM(CASE
		        WHEN DATE(REG_DT) = CURDATE() THEN 1
		        ELSE 0
		    END) AS todayCount,
		    SUM(CASE
		        WHEN REG_DT >= DATE_SUB(CURDATE(), INTERVAL 6 DAY) THEN 1
		        ELSE 0
		    END) AS recentCount,
		    (
		        SELECT COUNT(*)
		        FROM TB_LANDING_ADHRNC A
    		  	, TB_LANDING_USER B
    		  	WHERE A.EVENT_ID = B.EVENT_ID 
		        AND RGTR_ID <> 'LANDING'
		    ) AS adminCount
		FROM TB_LANDING_ADHRNC A
    		  	, TB_LANDING_USER B
		WHERE A.EVENT_ID = B.EVENT_ID 
		AND A.USE_AT = 'Y'
        """)
    AdhrncMngSummaryVO selectAdhrncMngSummary();

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
	        ,  REPLACE(#{moblphon}, '-', '')
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
	        , 'MNGR'
	        , NOW()
        )
        """)
    @Options(useGeneratedKeys = true, keyProperty = "adhrncNo")
    int insertAdhrncMng(AdhrncVO adhrncVO);

    @Update("""
        UPDATE TB_LANDING_ADHRNC
        SET
	        ADHRNC_NM = #{adhrncNm}
            , MOBLPHON =  REPLACE(#{moblphon}, '-', '')
	        , EMAIL = #{email}
	        , CUST_NM_01 = #{custNm01}
	        , CUST_NM_02 = #{custNm02}
	        , CUST_NM_03 = #{custNm03}
	        , CUST_NM_04 = #{custNm04}
	        , CUST_NM_05 = #{custNm05}
	        , CUST_NM_06 = #{custNm06}
	        , CUST_NM_07 = #{custNm07}
	        , CUST_NM_08 = #{custNm08}
	        , CUST_NM_09 = #{custNm09}
	        , CUST_NM_10 = #{custNm10}
	        , CUST_NM_11 = #{custNm11}
	        , CUST_NM_12 = #{custNm12}
	        , CUST_NM_13 = #{custNm13}
	        , CUST_NM_14 = #{custNm14}
	        , CUST_NM_15 = #{custNm15}            
            , MDFR_ID = 'MNGR'
            , MDFCN_DT = NOW()
        WHERE ADHRNC_NO = #{adhrncNo}
		AND  EVENT_ID = (SELECT EVENT_ID FROM TB_EVENT_MANAGE WHERE USE_AT = 'Y')
		AND USE_AT = 'Y'
        """)
    int updateAdhrncMng(AdhrncVO adhrncVO);

    @Update("""
        UPDATE TB_LANDING_ADHRNC
        SET USE_AT = 'N'
        WHERE ADHRNC_NO = #{adhrncNo}
        AND  EVENT_ID = (SELECT EVENT_ID FROM TB_EVENT_MANAGE WHERE USE_AT = 'Y')
    	AND USE_AT = 'Y'
        """)
    int deleteAdhrncMng(@Param("adhrncNo") AdhrncVO vo);
}