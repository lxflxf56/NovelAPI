package cn.stormzi.novelapi.service.cache;

import cn.stormzi.novelapi.facade.cache.ESFacade;
import cn.stormzi.novelapi.util.elasticsearch.ESUtil;
import com.alibaba.fastjson.JSON;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-12
 **/

@Service("esService")
public class ESService implements ESFacade {




    protected static final Logger logger = LoggerFactory.getLogger(ESService.class);


    @Override
    public boolean exist(String index, String type, long id) {
        return false;
    }

    @Override
    public String select(String index, String type, long id) throws IOException {
        String path = createPath(index, type, id + "");
        Response response = ESUtil.request(new Request("get", path));
        return EntityUtils.toString(response.getEntity());
    }

    public String select(String index, String type, String json) throws IOException {
        if (index == null) {//为了数据库运行安全必须加index
            return null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("/").append(index);
        if (type != null) {//type可为null
            builder.append("/").append(type);
        }
        String path = builder.append("/").append("_search").toString();
        Response response = ESUtil.post(path, json);
        return EntityUtils.toString(response.getEntity());
    }

    public void bulk(String json){
        String path="/_bulk";
        ESUtil.postAsync(path,json,new DefaultResponseListener(path+" "+json));
    }

    
    /**
    * @Description: 批量操作 https://www.elastic.co/guide/cn/elasticsearch/guide/current/bulk.html
    * @Author: Xiaofeng
    * @Date: 2018/12/13
    */ 
    public void bulk(List list){
        StringBuilder builder=new StringBuilder();
        for (int i=0;i<list.size();i++){
            builder.append(JSON.toJSONString(list.get(i))).append("\n");
        }
        String json = builder.toString();
        bulk(json);
    }


    public void insert(String index, String type, long id, Map par) {
        String path = createPath(index, type, id + "");
        if (path==null){
            return;
        }
        String json = JSON.toJSONString(par);
        ESUtil.postAsync(path, json, new DefaultResponseListener(path+" "+json));
    }
    public void insert(String index, String type, long id, String json) {
        String path = createPath(index, type, id + "");
        if (path==null){
            return;
        }
        ESUtil.postAsync(path, json, new DefaultResponseListener(path+" "+json));
    }


    public void delect(String index, String type, long id, Map par){
        String path = createPath(index, type, id + "");
        if (path==null){
            return;
        }
        String json = JSON.toJSONString(par);
        ESUtil.deleteAsync(path,json,new DefaultResponseListener(path+" "+json));
    }

    public void delect(String index, String type, long id){
        String path = createPath(index, type, id + "");
        if (path==null){
            return;
        }
        ESUtil.deleteAsync(path,"",new DefaultResponseListener(path));
    }
    
    


    
    /**
    * @Description: 文档部分更新
    * @Author: Xiaofeng
    * @Date: 2018/12/13 
    */ 
    public void update(String index, String type, long id, Map par) {
        String path = createPath(index, type, id + "")+"/_update";
        if (path==null){
            return;
        }
        String json = JSON.toJSONString(par);
        ESUtil.postAsync(path, json,new DefaultResponseListener(path+" "+json));
    }

    public void update(String index, String type, long id, String json) {
        String path = createPath(index, type, id + "")+"/_update";
        if (path==null){
            return;
        }
        ESUtil.postAsync(path, json,new DefaultResponseListener(path+" "+json));
    }
    
    /**
    * @Description: 文档全部更新 
    * @Author: Xiaofeng
    * @Date: 2018/12/13 
    */
    public void updateAll(String index, String type, long id, String json) {
        String path = createPath(index, type, id + "");
        if (path==null){
            return;
        }
        ESUtil.putAsync(path, json,new DefaultResponseListener(path+" "+json));
    }



    public String createPath(String index,String type,String operate){
        if (StringUtils.isEmpty(index)||StringUtils.isEmpty(type)||StringUtils.isEmpty(operate)) {//为了数据库运行安全必须加index
            logger.error("null Error：index: {} ,type: {} ,operate: {} ", index, type,operate);
            return null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("/").append(index);
        builder.append("/").append(type);
        String path = builder.append("/").append(operate).toString();
        return path;
    }


    class DefaultResponseListener implements ResponseListener {
        private final String info;

        public DefaultResponseListener(String info){
            this.info=info;
        }

        @Override
        public void onSuccess(Response response) {
            logger.info("insert info:{} ,response: {} ",info,response.toString());
        }

        @Override
        public void onFailure(Exception e) {
            logger.error("insert info:{} ,e: {}",info,e.toString());
        }
    }
}
