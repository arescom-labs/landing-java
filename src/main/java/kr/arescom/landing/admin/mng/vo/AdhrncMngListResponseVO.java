package kr.arescom.landing.admin.mng.vo;

import java.util.List;

import kr.arescom.landing.adhrnc.vo.AdhrncVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdhrncMngListResponseVO {

    private List<AdhrncVO> list;
    private int totalCount;
}