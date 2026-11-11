package kr.arescom.landing.admin.mng.dto;

import java.util.List;

import kr.arescom.landing.admin.mng.vo.AdhrncMngSearchVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExcelDownloadRequest {

    private AdhrncMngSearchVO searchVO;

    private List<ExcelColumnDto> columns;
}