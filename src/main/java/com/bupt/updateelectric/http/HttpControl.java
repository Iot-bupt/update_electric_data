package com.bupt.updateelectric.http;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HttpControl {
    static private Cookie ck;
    private String host = "39.104.84.131";
    private static String session;
    public static String id;
    public String deviceToken;

    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
    private static final MediaType js = MediaType.parse("application/json; charset=utf-8");

    ///创建okHttpClient对象
    private OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .cookieJar(new CookieJar() {
                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    cookieStore.put(url.host(), cookies);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    List<Cookie> cookies = cookieStore.get(url.host());
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            })
            .build();


    /*
    登录时的post请求
     */
    public void httplogin() throws IOException {
        cookieStore.clear();
        //请求体
        RequestBody bodyLogin = RequestBody.create(js, "{\"username\":\"dianwang@bupt.edu.cn\",\"password\":\"password\"}");

        //创建一个Request Request是OkHttp中访问的请求，Builder是辅助类。Response即OkHttp中的响应。
        final Request requestLogin = new Request.Builder()
                .url("http://39.104.84.131/api/user/login")
                .header("Accept","text/plain, */*; q=0.01")
                .addHeader("Connection","keep-alive")
                .addHeader("Content-Type","application/json; charset=UTF-8")
                .post(bodyLogin)
                .build();
        //得到一个call对象
        Response response = mOkHttpClient.newCall(requestLogin).execute();
        if(response.isSuccessful()){
            Headers headers = response.headers();
            ck = cookieStore.get(host).get(0);

            String sessionStr = ck.toString();
            session = sessionStr.substring(0,sessionStr.indexOf(";"));

            System.out.println("Login ck is :"+ck);
            System.out.println("Login session is :"+session);
        }
        //请求加入调度
/*        call.execute(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("login请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Headers headers = response.headers();
                ck = cookieStore.get(host).get(0);

                String sessionStr = ck.toString();
                session = sessionStr.substring(0,sessionStr.indexOf(";"));

                System.out.println("Login ck is :"+ck);
                System.out.println("Login session is :"+session);
            }
        });*/

    }

    /*
    创建新设备的post请求
     */

    public String httpcreate(final String devicename) throws Exception{

        //请求体
        JSONObject obj = new JSONObject();
        obj.put("name",devicename);
        RequestBody bodyCreate = RequestBody.create(js, obj.toString());

        //创建一个Request Request是OkHttp中访问的请求，Builder是辅助类。Response即OkHttp中的响应。
        Request requestCreate = new Request.Builder()
                .url("http://39.104.84.131/api/device/create")
                .post(bodyCreate)
                .addHeader("Accept","application/json, text/plain, */*")
//                .addHeader("Accept","text/plain, */*, q=0.01")
                .addHeader("Connection","keep-alive")
                .addHeader("Content-Type","application/json;charset=UTF-8")
                .addHeader("Cookie",session.toString())
                .build();
        //得到一个call对象
        Response response = mOkHttpClient.newCall(requestCreate).execute();
        if (response.isSuccessful()){
            String result = response.body().string();

            JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
            id = jsonObject.get("id").getAsString();
            System.out.println("id is :"+id);
            return id;
        }else{
            httplogin();
            return null;
        }

    }

    /*
    查找令牌的get请求
     */

    public String httpfind(String id)throws Exception {

        //创建一个Request Request是OkHttp中访问的请求，Builder是辅助类。Response即OkHttp中的响应。
        Request requestCreate = new Request.Builder()
                .url("http://39.104.84.131/api/device/token/" + id.toString())
                .get()
                .addHeader("Accept", "application/json, text/plain, */*")
                .addHeader("Connection", "keep-alive")
                .addHeader("Cookie", session.toString())
                .build();
        //得到一个call对象
        Response response = mOkHttpClient.newCall(requestCreate).execute();
        if (response.isSuccessful()) {

            String result = response.body().string();
//            Log.e("http", "find_response : " + result);

            JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
            deviceToken = jsonObject.get("deviceToken").getAsString();
            System.out.println("find_token is :"+deviceToken);
            return deviceToken;
        }
        return null;


    }
}
