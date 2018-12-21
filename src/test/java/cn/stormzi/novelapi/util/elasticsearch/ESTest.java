package cn.stormzi.novelapi.util.elasticsearch;

import cn.stormzi.novelapi.facade.bean.cache.ChaptersBean;
import cn.stormzi.novelapi.facade.bean.cache.PageBean;
import com.alibaba.fastjson.JSON;
import org.apache.http.util.EntityUtils;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Maps;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-12
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class ESTest {


    public void test(){
        Object a=new PageBean();
        Object b=new ChaptersBean();

        System.out.println(a.getClass());
        System.out.println(b.getClass());
    }



    public void req() throws IOException {
        //Response response = ESUtil.get("/filepan/filepan/_search");
        Map map= Maps.newHashMap("name","nihao");
        String s = JSON.toJSONString(map);
        System.out.println(s);
        Response response = ESUtil.request(new Request("get", "/filepan/_search"));
        System.out.println(response.toString());
        System.out.println(EntityUtils.toString(response.getEntity()));
        response = ESUtil.request(new Request("get", "/filepan/doc/114807"));
        System.out.println(response.toString());
        System.out.println(EntityUtils.toString(response.getEntity()));
        ESUtil.request(new Request("post","/filepan/doc/_search"));
    }

    public void query(){
        QueryBuilder.NodeParent nodeParent = new QueryBuilder.NodeParent();


//        nodeParent.addNode(
//                new QueryBuilder.Node("name","nihao")).addNode(
//                            new QueryBuilder.NodeParent("haole")
//                                    .addNode(new QueryBuilder.Node("name","你好"))
//                    );
        nodeParent.addNode(new QueryBuilder.Node("name", Lists.newArrayList(1,2,3,4)));
        System.out.println(nodeParent.toString());
    }


    public void json(){
        //String term = QueryBuilder.term("name", "nihao");
        //System.out.println(term);


    }
}
