package kr.arescom.landing.admin.mng.web;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.arescom.landing.adhrnc.vo.AdhrncVO;
import kr.arescom.landing.admin.mng.service.AdhrncMngService;
import kr.arescom.landing.admin.mng.vo.AdhrncMngListResponseVO;
import kr.arescom.landing.admin.mng.vo.AdhrncMngSearchVO;
import kr.arescom.landing.admin.mng.vo.AdhrncMngSummaryVO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdhrncMngController {

    private final AdhrncMngService adhrncMngService;

    /**
     * 사전등록 목록 조회
     */
    @PostMapping("/list")
    public ResponseEntity<AdhrncMngListResponseVO> selectAdhrncMngList(
            @RequestBody AdhrncMngSearchVO searchVO) {

        return ResponseEntity.ok(
                adhrncMngService.selectAdhrncMngList(searchVO)
        );
    }

    /**
     * 사전등록 현황 조회
     */
    @PostMapping("/summary")
    public ResponseEntity<AdhrncMngSummaryVO> selectAdhrncMngSummary() {

        return ResponseEntity.ok(
                adhrncMngService.selectAdhrncMngSummary()
        );
    }

    /**
     * 사전등록 등록
     */
    @PostMapping("/insert")
    public ResponseEntity<Map<String, Object>> insertAdhrncMng(
            @RequestBody AdhrncVO vo) {

        Map<String, Object> result = new HashMap<>();

        int count = adhrncMngService.insertAdhrncMng(vo);

        if (count > 0) {
            result.put("result", "success");
            result.put("message", "등록되었습니다.");
        } else {
            result.put("result", "fail");
            result.put("message", "등록에 실패했습니다.");
        }

        return ResponseEntity.ok(result);
    }

    /**
     * 사전등록 수정
     */
    @PostMapping("/update")
    public ResponseEntity<Map<String, Object>> updateAdhrncMng(
            @RequestBody AdhrncVO vo) {

        Map<String, Object> result = new HashMap<>();

        int count = adhrncMngService.updateAdhrncMng(vo);

        if (count > 0) {
            result.put("result", "success");
            result.put("message", "수정되었습니다.");
        } else {
            result.put("result", "fail");
            result.put("message", "수정 대상이 존재하지 않습니다.");
        }

        return ResponseEntity.ok(result);
    }

    /**
     * 사전등록 삭제
     */
    @PostMapping("/delete")
    public ResponseEntity<Map<String, Object>> deleteAdhrncMng(
            @RequestBody AdhrncVO vo) {

        Map<String, Object> result = new HashMap<>();

        int count = adhrncMngService.deleteAdhrncMng(vo);

        if (count > 0) {
            result.put("result", "success");
            result.put("message", "삭제되었습니다.");
        } else {
            result.put("result", "fail");
            result.put("message", "삭제 대상이 존재하지 않습니다.");
        }

        return ResponseEntity.ok(result);
    }

    /**
     * 사전등록 엑셀 다운로드
     */
    @PostMapping("/excel")
    public ResponseEntity<byte[]> selectAdhrncMngExcel(
            @RequestBody AdhrncMngSearchVO searchVO) throws IOException {

        byte[] excelBytes =
                adhrncMngService.selectAdhrncMngExcel(searchVO);

        ContentDisposition contentDisposition =
                ContentDisposition.attachment()
                        .filename("사전등록목록.xlsx", StandardCharsets.UTF_8)
                        .build();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentDisposition(contentDisposition);
        headers.setContentType(
                MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                )
        );
        headers.setContentLength(excelBytes.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelBytes);
    }
}
