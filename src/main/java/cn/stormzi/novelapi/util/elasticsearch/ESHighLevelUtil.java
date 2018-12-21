package cn.stormzi.novelapi.util.elasticsearch;

import cn.stormzi.novelapi.NovelapiApplication;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-14
 **/


public class ESHighLevelUtil {


    private static boolean isHighLevel=false;
    private static RestHighLevelClient client;


    public static void init() {
        isHighLevel = !ESUtil.isLowLevel;
        if (NovelapiApplication.useCache && isHighLevel && ESUtil.getRestClient() == null) {
            client = generateRestClient(ESUtil.generateHttpHosts());
        }
    }

    private static RestHighLevelClient generateRestClient(HttpHost[] httpHosts) {
        return new RestHighLevelClient(
                RestClient.builder(httpHosts));
    }

    public static void closeClient() {
        if (client != null) {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void insert() {

    }

    public void batchinsert() {

    }


}
