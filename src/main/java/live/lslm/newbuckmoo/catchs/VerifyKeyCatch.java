package live.lslm.newbuckmoo.catchs;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 验证码缓存池
 */
public class VerifyKeyCatch {
    private static Map<String, String> map = Maps.newHashMap();
    public static void putVerifyKey(String phoneNumber, String verifyKey){
        map.put(phoneNumber, verifyKey);
    }

    public static String getVerifyKey(String phoneNumber){
        return map.get(phoneNumber);
    }

    public static boolean rmVerifyKey(String phoneNumber){
        return map.remove(phoneNumber) != null;
    }
}