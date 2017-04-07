package tools;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 
 * @author chk
 *
 */

public class OKHttpUtil {


    private static OkHttpClient mOkHttpClient = new OkHttpClient();

    /**
     * 
     * @param url 请求的网址
     * @param hashMap 将参数保存在这个hashmap里面
     * @return 返回请求后的结果
     * @throws IOException
     */
    public static Response getRequest(String url,HashMap<String,String> hashMap) throws IOException {
    	if (hashMap != null) {
            url += "?";
                for(String key:hashMap.keySet()) {
                    url += key + "=" +hashMap.get(key) + "&";
                }
            }
        Request request = new Request.Builder()
                .url(url)
                .build();
        return mOkHttpClient.newCall(request).execute();
    } 

}
