package cn.tedu.knows.kafka;


import com.google.gson.Gson;
import net.minidev.json.JSONObject;

public class JsonTest {
    public static void main(String[] args) {

        String SEVENS_GG_BILLING_INFO_DETAIL = "{\"customerId\":6610986860,\"status\":true,\"message\":\"customers/6610986860/billingSetups/6841094730\",\"billingSetup\":\"customers/6610986860/billingSetups/6841094730\",\"createTime\":\"2022-10-28 16:24:03\"}";
        Gson gson=new Gson();
        JSONObject obj = gson.fromJson(SEVENS_GG_BILLING_INFO_DETAIL, JSONObject.class);
        obj.put("customerId",10002);
        System.out.println(obj);
    }
}
