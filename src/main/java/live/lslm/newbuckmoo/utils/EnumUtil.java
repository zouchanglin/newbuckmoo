package live.lslm.newbuckmoo.utils;

import live.lslm.newbuckmoo.enums.CodeEnum;

public class EnumUtil {
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each: enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }

    public static <T> T[] getAllEnums(Class<T> enumClass) {
        return enumClass.getEnumConstants();
    }
}