package cn.stormzi.novelapi.util.http;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-05
 **/


public class HttpUtil {
    public static final String GET="GET",POST="POST",DELETE="DELETE",PUT="PUT";
    public static final MediaType HEADER = MediaType.parse("application/json;");
    protected static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private static OkHttpClient okHttpClient;

    public static String get(String url) throws IOException {
        String connect = connect(GET, url, null);
        return connect;
    }

    public static String post(String url) throws IOException {
        String connect = connect(POST, url, "{}");
        return connect;
    }


    public static String post(String url,String json) throws IOException {
        String connect = connect(POST, url, json);
        return connect;
    }

    public static String put(String url) throws IOException {
        String connect = connect(PUT, url, "{}");
        return connect;


    }
    public static String put(String url,String json) throws IOException {
        String connect = connect(PUT, url, json);
        return connect;
    }

    public static String delete(String url) throws IOException {
        String connect = connect(DELETE, url, "{}");
        return connect;


    }
    public static String delete(String url,String json) throws IOException {
        String connect = connect(DELETE, url, json);
        return connect;
    }

    public static void get(String url,Callback callback){
        connect("get",url,null,callback);
    }

    public static void post(String url,String json,Callback callback){
        connect("get",url,json,callback);
    }

    public static void connect(String method,String url,String json,Callback callback){
        OkHttpClient okHttpClient = getOkHttpClient();
        RequestBody body = null;
        if (null!=json){
            body = RequestBody.create(HEADER,json);
        }
        Response execute = null;
        Request request=new Request.Builder().url(url).method(method,body).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static String connect(String method,String url,String json) throws IOException {
        OkHttpClient okHttpClient = getOkHttpClient();
        RequestBody body = null;
        if (null!=json){
            body = RequestBody.create(HEADER,json);
        }
        Response execute = null;
        Request request=new Request.Builder().url(url).method(method,body).build();

        execute= okHttpClient.newCall(request).execute();
        if (execute.isSuccessful()){
            return execute.body().string();
        }else {
            logger.warn("Code:{} , message:{} ",execute.code(),execute.message());
        }
        return null;
    }

    public static OkHttpClient getOkHttpClient() {
        if (null==okHttpClient){
            synchronized (HttpUtil.class){
                if (null==okHttpClient){
                    okHttpClient=new OkHttpClient();
                }
            }
        }
        return okHttpClient;
    }

    public static void setOkHttpClient(OkHttpClient okHttpClient) {
        HttpUtil.okHttpClient = okHttpClient;
    }
}
