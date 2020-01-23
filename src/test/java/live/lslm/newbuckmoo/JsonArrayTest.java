package live.lslm.newbuckmoo;

import com.alibaba.fastjson.JSONObject;

public class JsonArrayTest {
    public static void main(String[] args) {
        Integer[] arr = new Integer[]{1,2,3,4,5};

        System.out.println(JSONObject.toJSON(arr));
    }
}
