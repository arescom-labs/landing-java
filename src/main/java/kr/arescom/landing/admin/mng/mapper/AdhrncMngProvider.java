package kr.arescom.landing.admin.mng.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import kr.arescom.landing.admin.mng.vo.AdhrncMngSearchVO;

public class AdhrncMngProvider {

    // 조회 컬럼 공통 정의
    private static final String SELECT_COLUMNS = """
        ADHRNC_NO AS adhrncNo,
        ADHRNC_NM AS adhrncNm,
        MOBLPHON AS moblphon,
        EMAIL AS email,
        CUST_NM_01 AS custNm01,
        CUST_NM_02 AS custNm02,
        CUST_NM_03 AS custNm03,
        CUST_NM_04 AS custNm04,
        CUST_NM_05 AS custNm05,
        CUST_NM_06 AS custNm06,
        CUST_NM_07 AS custNm07,
        CUST_NM_08 AS custNm08,
        CUST_NM_09 AS custNm09,
        CUST_NM_10 AS custNm10,
        CUST_NM_11 AS custNm11,
        CUST_NM_12 AS custNm12,
        CUST_NM_13 AS custNm13,
        CUST_NM_14 AS custNm14,
        CUST_NM_15 AS custNm15,
        CASE WHEN RGTR_ID = 'LANDING' THEN '등록페이지'
    		 ELSE '관리자'
    	END rgtrSe,
        DATE_FORMAT(REG_DT, '%Y-%m-%d %H:%i:%s') AS regDt,
        USE_AT AS useAt
        """;

    /**
     * 공통 검색 조건
     */
    private SQL createBaseSql(AdhrncMngSearchVO searchVO) {
        SQL sql = new SQL();
        sql.FROM("TB_LANDING_ADHRNC A");
        sql.FROM("TB_LANDING_USER B");
        sql.WHERE("A.EVENT_ID = B.EVENT_ID");
        sql.WHERE("A.USE_AT = 'Y'");

        if (searchVO.getKeyword() != null && !searchVO.getKeyword().trim().isEmpty()) {
            sql.WHERE("""
                (
                    ADHRNC_NM LIKE CONCAT('%', #{keyword}, '%')
                    OR MOBLPHON LIKE CONCAT('%', #{keyword}, '%')
                    OR CUST_NM_01 LIKE CONCAT('%', #{keyword}, '%')
                    OR CUST_NM_02 LIKE CONCAT('%', #{keyword}, '%')
                )
                """);
        }
        return sql;
    }

    /**
     * 정렬 조건
     */
    private String getOrderBy(String sortType) {
        return switch (sortType == null ? "new" : sortType) {
            case "old" -> "REG_DT ASC";
            case "name" -> "ADHRNC_NM ASC";
            case "custNm01" -> "CUST_NM_01 ASC";
            default -> "REG_DT DESC";
        };
    }

    /**
     * 목록 조회
     */
    public String selectAdhrncMngList(AdhrncMngSearchVO searchVO) {
        SQL sql = createBaseSql(searchVO);
        sql.SELECT(SELECT_COLUMNS);
        return sql.toString()
                + " ORDER BY " + getOrderBy(searchVO.getSortType())
                + " LIMIT #{pageSize} OFFSET #{offset}";
    }

    /**
     * 전체 건수 조회
     */
    public String selectAdhrncMngCount(AdhrncMngSearchVO searchVO) {
        SQL sql = createBaseSql(searchVO);
        sql.SELECT("COUNT(*)");
        return sql.toString();
    }

    /**
     * 엑셀 다운로드 전체 목록 조회
     * 페이징 없이 검색 조건에 해당하는 전체 데이터 조회
     */
    public String selectAdhrncMngExcelList(Map<String, Object> params) {

        AdhrncMngSearchVO searchVO =
            (AdhrncMngSearchVO) params.get("searchVO");

        @SuppressWarnings("unchecked")
        List<String> columns = (List<String>) params.get("columns");

        SQL sql = createBaseSql(searchVO);

        for (String column : columns) {
            sql.SELECT(column);
        }

        return sql.toString() + " ORDER BY " + getOrderBy(searchVO.getSortType());
    }
}