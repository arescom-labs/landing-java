package kr.arescom.landing.adhrnc.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.arescom.landing.adhrnc.service.AdhrncService;
import kr.arescom.landing.adhrnc.vo.AdhrncVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/portal/adhrnc")
@RequiredArgsConstructor
public class AdhrncController {
	
	private final AdhrncService service;
	
    @PostMapping("/regist")
    public ResponseEntity<Map<String, Object>> regist(@RequestBody AdhrncVO vo) {
    	Map<String, Object> response = new HashMap<>();
        try {
            // 단건을 리스트로 만들어서 서비스 호출
            int result = service.insertAdhrnc(vo);
            response.put("result", "S");
            response.put("msg", "성공적으로 등록되었습니다.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("result", "F");
            response.put("msg", "등록 중 오류 발생: " + e.getMessage());
            throw new RuntimeException("등록 중 오류 발생: " + e.getMessage(), e);
        }
    }
}
