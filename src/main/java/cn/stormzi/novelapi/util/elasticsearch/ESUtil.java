package cn.stormzi.novelapi.util.elasticsearch;

import cn.stormzi.novelapi.NovelapiApplication;
import com.alibaba.fastjson.JSON;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-12
 **/


public class ESUtil {
    private static RestClient restClient;
    public static boolean isLowLevel=true;


    /*
    static {
        if (NovelapiApplication.useCache) {
            if (isLowLevel){
                restClient = generateRestClient(generateHttpHosts());
            }else {
                ESHighLevelUtil.init();
            }
        }
    }
    */

    public static RestClient generateRestClient(HttpHost[] httpHosts) {
        try {
            //配置可选参数
            RestClientBuilder builder = RestClient.builder(httpHosts);
            //设置每个请求需要发送的默认headers，这样就不用在每个请求中指定它们。
            Header[] defaultHeaders = new Header[]{new BasicHeader("Content-Type", "application/json; charset=utf-8")};
            builder.setDefaultHeaders(defaultHeaders);
            // 设置应该授予的超时时间，以防对相同的请求进行多次尝试。默认值是30秒，与默认socket超时时间相同。
            // 如果自定义socket超时时间，则应相应地调整最大重试超时时间。
            builder.setMaxRetryTimeoutMillis(10000);
            RestClient restClient = builder.build();
            return restClient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static RestClient getRestClient() {
        if (restClient==null&&NovelapiApplication.useCache){
            synchronized (ESUtil.class){
                if (restClient==null&&NovelapiApplication.useCache){
                    restClient=generateRestClient(generateHttpHosts());
                }
            }
        }
        return restClient;
    }

    public static HttpHost[] generateHttpHosts() {
        List<HttpHost> list = new ArrayList();
        list.add(generateHttpHost("localhost:9200"));
        HttpHost[] httpHosts=new HttpHost[1];
        return list.toArray(httpHosts);
    }

    public static HttpHost generateHttpHost(String address){
        String[] split = address.split(":");
        if (split.length>1){
            int port = Integer.parseInt(split[1]);
            return new HttpHost(split[0],port,"http");
        }
        return new HttpHost(address);
    }


    /**
     * @Description: 异步方法
     * @Author: Xiaofeng
     * @Date: 2018/12/13
     */
    public static void requestAsync(Request request, ResponseListener responseListener){
        getRestClient().performRequestAsync(request, responseListener);
    }

    public static void requestAsync(Request request, Map map, ResponseListener responseListener) {
        String json = JSON.toJSONString(map);
        requestAsync(request, json, responseListener);
    }

    public static void requestAsync(Request request, String json, ResponseListener responseListener) {
        request.setJsonEntity(json);
        requestAsync(request, responseListener);
    }

    public static void postAsync(String path, String json, ResponseListener responseListener) {
        Request request = new Request("post", path);
        requestAsync(request, json, responseListener);
    }

    public static void postAsync(String path, Map map, ResponseListener responseListener) {
        Request request = new Request("post", path);
        requestAsync(request, map, responseListener);
    }

    public static void putAsync(String path, Map map, ResponseListener responseListener) {
        Request request = new Request("put", path);
        requestAsync(request, map, responseListener);
    }

    public static void putAsync(String path, String json, ResponseListener responseListener) {
        Request request = new Request("put", path);
        requestAsync(request, json, responseListener);
    }

    public static void deleteAsync(String path, String json, ResponseListener responseListener) {
        Request request = new Request("delete", path);
        requestAsync(request, json, responseListener);
    }

    public static void deleteAsync(String path, Map map, ResponseListener responseListener) {
        Request request = new Request("delete", path);
        requestAsync(request, map, responseListener);
    }


    /**
     * @Description: 同步方法
     * @Author: Xiaofeng
     * @Date: 2018/12/13
     */
    public static Response request(Request request) throws IOException {
        Response response = getRestClient().performRequest(request);
        return response;
    }

    @Deprecated
    public static Response get(String path) throws IOException {
        Response response = getRestClient().performRequest("GET", path);
        return response;
    }

    public static Response post(String path, Map map) throws IOException {
        String json = JSON.toJSONString(map);
        return post(path, json);

    }

    public static Response put(String path, Map map) throws IOException {
        String json = JSON.toJSONString(map);
        return put(path, json);

    }

    public static Response delete(String path, Map map) throws IOException {
        String json = JSON.toJSONString(map);
        return delete(path, json);
    }

    public static Response post(String path, String json) throws IOException {
        Request request = new Request("post", path);
        request.setJsonEntity(json);
        Response response = request(request);
        return response;
    }

    public static Response put(String path, String json) throws IOException {
        Request request = new Request("put", path);
        request.setJsonEntity(json);
        Response response = request(request);
        return response;
    }

    public static Response delete(String path, String json) throws IOException {
        Request request = new Request("delete", path);
        request.setJsonEntity(json);
        Response response = request(request);
        return response;
    }

}
