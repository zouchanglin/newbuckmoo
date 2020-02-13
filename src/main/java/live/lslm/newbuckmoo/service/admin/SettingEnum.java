package live.lslm.newbuckmoo.service.admin;

public enum SettingEnum{
    NEW_STUDENT("new_student"),
    NEW_COMPANY("new_company"),
    NEW_CLUB("new_club"),
    RECOMMEND_CLUB("recommend_club"),
    RECOMMEND_STUDENT("recommend_student"),
    RECOMMEND_COMPANY("recommend_company");

    private String key;

    SettingEnum(String key){
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
