package kr.arescom.landing.admin.mng.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdhrncMngSearchVO {
    private String keyword;
    private String sortType;

    private Integer page = 1;
    private Integer pageSize = 15;

    public int getOffset() {
        return (Math.max(page, 1) - 1) * Math.max(pageSize, 1);
    }
}
