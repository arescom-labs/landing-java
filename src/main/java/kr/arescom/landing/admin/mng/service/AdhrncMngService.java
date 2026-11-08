package kr.arescom.landing.admin.mng.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.arescom.landing.adhrnc.vo.AdhrncVO;
import kr.arescom.landing.admin.mng.mapper.AdhrncMngMapper;
import kr.arescom.landing.admin.mng.vo.AdhrncMngListResponseVO;
import kr.arescom.landing.admin.mng.vo.AdhrncMngSearchVO;
import kr.arescom.landing.admin.mng.vo.AdhrncMngSummaryVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdhrncMngService {

    private final AdhrncMngMapper adhrncMngMapper;

    // 신청자 목록 조회
    public AdhrncMngListResponseVO selectAdhrncMngList(AdhrncMngSearchVO searchVO) {

        validateSearch(searchVO);

        AdhrncMngListResponseVO responseVO = new AdhrncMngListResponseVO();

        responseVO.setList(adhrncMngMapper.selectAdhrncMngList(searchVO));
        responseVO.setTotalCount(adhrncMngMapper.selectAdhrncMngCount(searchVO));

        return responseVO;
    }

    // 대시보드 통계 조회
    public AdhrncMngSummaryVO selectAdhrncMngSummary() {
        return adhrncMngMapper.selectAdhrncMngSummary();
    }

    // 신청자 등록
    @Transactional
    public int insertAdhrncMng(AdhrncVO adhrncVO) {

//        validateAdhrnc(adhrncVO);

        return adhrncMngMapper.insertAdhrncMng(adhrncVO);
    }

    // 신청자 수정
    @Transactional
    public int updateAdhrncMng(AdhrncVO adhrncVO) {

        if (adhrncVO.getAdhrncNo() == null) {
            throw new IllegalArgumentException("신청자 번호가 없습니다.");
        }

//        validateAdhrnc(adhrncVO);

        int result = adhrncMngMapper.updateAdhrncMng(adhrncVO);

        if (result == 0) {
            throw new IllegalArgumentException("수정할 신청자 정보가 없습니다.");
        }
        
        return result;
    }

    // 신청자 삭제
    @Transactional
    public int deleteAdhrncMng(AdhrncVO vo) {

        if (vo == null) {
            throw new IllegalArgumentException("신청자 번호가 없습니다.");
        }

        int result = adhrncMngMapper.deleteAdhrncMng(vo);

        if (result == 0) {
            throw new IllegalArgumentException("삭제할 신청자 정보가 없습니다.");
        }
        return result;
    }

    // 엑셀 다운로드
    public byte[] selectAdhrncMngExcel(AdhrncMngSearchVO searchVO) throws IOException {

        validateSearch(searchVO);

        // 엑셀은 페이징 없이 검색 조건에 해당하는 전체 데이터 조회
        var list = adhrncMngMapper.selectAdhrncMngExcelList(searchVO);

        try (
            Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        ) {

            Sheet sheet = workbook.createSheet("신청자 목록");

            // ==============================
            // 헤더 스타일
            // ==============================
//            CellStyle headerStyle = workbook.createCellStyle();

            XSSFCellStyle headerStyle = (XSSFCellStyle) workbook.createCellStyle();

            headerStyle.setFillForegroundColor(
                new XSSFColor(
                    new java.awt.Color(241, 245, 249),
                    new DefaultIndexedColorMap()
                )
            );
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // 테두리
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            // 가운데 정렬
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // 헤더 폰트
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontName("맑은 고딕");
            headerFont.setFontHeightInPoints((short) 10);

            headerStyle.setFont(headerFont);

            // ==============================
            // 데이터 스타일
            // ==============================
            CellStyle dataStyle = workbook.createCellStyle();

            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);

            dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            Font dataFont = workbook.createFont();
            dataFont.setFontName("맑은 고딕");
            dataFont.setFontHeightInPoints((short) 10);

            dataStyle.setFont(dataFont);

            // ==============================
            // 헤더 생성
            // ==============================
            String[] headers = {
                "번호",
                "신청일시",
                "성명",
                "연락처",
                "소속",
                "선박명"
            };

            Row headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints(25);

            for (int i = 0; i < headers.length; i++) {

                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);

            }

            // ==============================
            // 데이터 생성
            // ==============================
            int rowNum = 1;

            for (AdhrncVO adhrncVO : list) {

                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(rowNum -1);
                row.createCell(1).setCellValue(nullToEmpty(adhrncVO.getRegDt()));
                row.createCell(2).setCellValue(nullToEmpty(adhrncVO.getAdhrncNm()));
                row.createCell(3).setCellValue(nullToEmpty(adhrncVO.getMoblphon()));
                row.createCell(4).setCellValue(nullToEmpty(adhrncVO.getCustNm01()));
                row.createCell(5).setCellValue(nullToEmpty(adhrncVO.getCustNm02()));

                for (int i = 0; i < headers.length; i++) {
                    row.getCell(i).setCellStyle(dataStyle);
                }

            }

            // ==============================
            // 열 너비 자동 조정
            // ==============================
            for (int i = 0; i < headers.length; i++) {

                sheet.autoSizeColumn(i);

                // 자동 조정 후 여유 너비 추가
                int width = sheet.getColumnWidth(i) + 512;

                // 엑셀 최대 열 너비 제한
                sheet.setColumnWidth(i, Math.min(width, 255 * 256));

            }

            // 파일 작성
            workbook.write(outputStream);

            return outputStream.toByteArray();

        }

    }

//    private void validateAdhrnc(AdhrncVO adhrncVO) {
//
//        if (adhrncVO == null) {
//            throw new IllegalArgumentException("신청자 정보가 없습니다.");
//        }
//
//        if (isBlank(adhrncVO.getAdhrncNm())) {
//            throw new IllegalArgumentException("성명을 입력해 주세요.");
//        }
//
//        if (isBlank(adhrncVO.getMoblphon())) {
//            throw new IllegalArgumentException("연락처를 입력해 주세요.");
//        }
//
//        if (isBlank(adhrncVO.getCustNm01())) {
//            throw new IllegalArgumentException("소속을 입력해 주세요.");
//        }
//    }

    private void validateSearch(AdhrncMngSearchVO searchVO) {

        if (searchVO == null) {
            throw new IllegalArgumentException("검색 조건이 없습니다.");
        }

        int page = searchVO.getPage() == null ? 1 : searchVO.getPage();
        int pageSize = searchVO.getPageSize() == null ? 15 : searchVO.getPageSize();

        if (page < 1) {
            searchVO.setPage(1);
        }

        if (pageSize < 1 || pageSize > 500) {
            searchVO.setPageSize(15);
        }
    }

//    private boolean isBlank(String value) {
//        return value == null || value.trim().isEmpty();
//    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}