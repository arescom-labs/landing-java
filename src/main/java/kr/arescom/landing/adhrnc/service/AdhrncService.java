package kr.arescom.landing.adhrnc.service;

import org.springframework.stereotype.Service;

import kr.arescom.landing.adhrnc.mapper.AdhrncMapper;
import kr.arescom.landing.adhrnc.vo.AdhrncVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor 
public class AdhrncService {
	private final AdhrncMapper adhrncMapper;
	
	public int insertAdhrnc(AdhrncVO vo) {
		return adhrncMapper.insertAdhrnc(vo);
	}

}
