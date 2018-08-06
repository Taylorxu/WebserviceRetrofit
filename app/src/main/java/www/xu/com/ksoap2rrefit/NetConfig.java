package www.xu.com.ksoap2rrefit;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.parkingwang.okhttp3.LogInterceptor.LogInterceptor;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 徐志广 on 2018/1/30.
 */

public class NetConfig implements Interceptor, CookieJar {
    private static NetConfig instance;

    private OkHttpClient client;
    private PersistentCookieStore cookieStore;
    private Gson gson;

    private NetConfig() {
    }

    public static NetConfig getInstance() {
        if (instance == null) {
            synchronized (NetConfig.class) {
                instance = new NetConfig();
            }
        }
        return instance;
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                getCookieStore().add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return getCookieStore().get(url);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        Response response = chain.proceed(builder.build());
        okhttp3.MediaType mediaType = response.body().contentType();
        if (null!=mediaType&&"text/xml;charset=UTF-8".equals(mediaType.toString())) {
            String content = response.body().string();
            Log.e("response.body()---", content);
            if (content.indexOf("<soap:Fault>") > -1) {
                return response;
            }
            String rebuildResult = content.substring(content.indexOf(">{") + 1, content.indexOf("</return>"));
            rebuildResult = rebuildResult.replace("&quot;", "\"");
            rebuildResult = rebuildResult.replace("\"returnValue\":\"\"", "\"returnValue\":null");

            return response.newBuilder().body(okhttp3.ResponseBody.create(mediaType, rebuildResult))
                    .build();
        }
        return response;
    }


    private PersistentCookieStore getCookieStore() {
        if (cookieStore == null)
            cookieStore = new PersistentCookieStore(WApp.getInstance().getApplicationContext());
        return cookieStore;
    }

    public OkHttpClient getClient() {
        if (client == null)
            client = new OkHttpClient.Builder()
                    .addInterceptor(new LogInterceptor())
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(this)
                    .build();
        return client;
    }

    public Gson getGson() {
        if (gson == null)
            gson = new GsonBuilder()
                    .enableComplexMapKeySerialization() //支持Map的key为复杂对象的形式
                    .create();
        return gson;
    }
}
