package cn.stormzi.novelapi.util.http;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-05
 **/


public class HttpUtil {
    public static final String GET = "GET", POST = "POST", DELETE = "DELETE", PUT = "PUT";
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


    public static String post(String url, String json) throws IOException {
        String connect = connect(POST, url, json);
        return connect;
    }

    public static String put(String url) throws IOException {
        String connect = connect(PUT, url, "{}");
        return connect;


    }

    public static String put(String url, String json) throws IOException {
        String connect = connect(PUT, url, json);
        return connect;
    }

    public static String delete(String url) throws IOException {
        String connect = connect(DELETE, url, "{}");
        return connect;


    }

    public static String delete(String url, String json) throws IOException {
        String connect = connect(DELETE, url, json);
        return connect;
    }

    public static void get(String url, Callback callback) {
        connect("get", url, null, callback);
    }

    public static void post(String url, String json, Callback callback) {
        connect("get", url, json, callback);
    }

    public static void connect(String method, String url, String json, Callback callback) {
        OkHttpClient okHttpClient = getOkHttpClient();
        RequestBody body = null;
        if (null != json) {
            body = RequestBody.create(HEADER, json);
        }
        Response execute = null;
        Request request = new Request.Builder().url(url).method(method, body).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static String connect(String method, String url, String json) throws IOException {
        OkHttpClient okHttpClient = getOkHttpClient();
        RequestBody body = null;
        if (null != json) {
            body = RequestBody.create(HEADER, json);
        }
        Response execute = null;
        Request request = new Request.Builder().url(url).method(method, body).build();

        execute = okHttpClient.newCall(request).execute();
        if (execute.isSuccessful()) {
            return execute.body().string();
        } else {
            logger.warn("Code:{} , message:{} ", execute.code(), execute.message());
        }
        return null;
    }

    public static OkHttpClient getOkHttpClient() {
        if (null == okHttpClient) {
            synchronized (HttpUtil.class) {
                if (null == okHttpClient) {
                    okHttpClient = new OkHttpClient();
                }
            }
        }
        return okHttpClient;
    }

    public static void setOkHttpClient(OkHttpClient okHttpClient) {
        HttpUtil.okHttpClient = okHttpClient;
    }


    public static void itrElement(int i, String[] s, Elements element, List result) {
        if (element.isEmpty()) {
            return;
        }
        if (i >= s.length) {
            result.add(element.get(0).html());
            return;
        }

        String str = s[i];
        if (isNum(str.charAt(0))) {
            //result.add(element.get(0).html());
            Element element1 = element.get(Integer.parseInt(str.charAt(0) + ""));
            if (str.length() > 1) {// 0[attr]
                str = str.substring(2, str.length() - 1);
                String attr = element1.attr(str);
                result.add(attr);
            } else {
                result.add(element1.html());
            }
            return;
        } else if (str.charAt(0) == '[') {
            str = str.substring(1, str.length() - 1);
            String attr = element.attr(str);
            result.add(attr);
        } else if (str.charAt(0) == '@') {
            str = str.substring(1);
            Elements select = element.select(str);
            for (Element element1 : select) {
                itrElement(i + 1, s, element1.children(), result);
            }
        } else {
            itrElement(i + 1, s, element.select(str), result);
        }

    }

    public static String itrElement(String str, Document document){
        if (StringUtils.isEmpty(str)){
            return "";
        }
        if (str.charAt(0) == '@'){
            str=str.substring(1);
        }
        Elements select = document.select(str);
        if (!select.isEmpty()){
            return select.get(0).html();
        }
        return "";
    }

    public static String itrElement(int i, String[] s, Elements element) {
        if (element.isEmpty()) {
            return "";
        }
        if (i >= s.length) {
            //result.add(element.get(0).html());
            return element.html();
        }
        String str = s[i];
        String result = null;
        if (isNum(str.charAt(0))) {
            Element element1 = element.get(Integer.parseInt(str.charAt(0) + ""));
            if (str.length() > 1) {// 0[attr]
                str = str.substring(2, str.length() - 1);
                result = element1.attr(str);
            } else {
                result = element1.html();
            }
        } else if (str.charAt(0) == '[') {
            str = str.substring(1, str.length() - 1);
            result = element.attr(str);
        } else if (str.charAt(0) == '@') {
            str = str.substring(1);
            result = itrElement(i + 1, s, element.select(str));
        } else {
            result = itrElement(i + 1, s, element.select(str));
        }
        return result;
    }


    public static boolean isNum(char c) {
        if (47 < c && c < 57) {
            return true;
        }
        return false;
    }
}
