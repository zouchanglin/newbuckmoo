package live.lslm.newbuckmoo.enums;

import lombok.Getter;

@Getter
public enum RecommendTypeEnum implements CodeEnum {
    STUDENT_RECOMMEND(0, "学生推荐"),
    COMPANY_RECOMMEND(1, "企业推荐"),
    CLUB_RECOMMEND(2, "社团推荐");

    private Integer code;
    private String message;

    RecommendTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
