package cn.stormzi.novelapi.util.elasticsearch;

import cn.stormzi.novelapi.facade.bean.cache.ContentBean;
import cn.stormzi.novelapi.facade.bean.cache.PageBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-12
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class ESTest {

    @Test
    public void test(){
        Object a=new PageBean();
        Object b=new ContentBean();

        System.out.println(a.getClass());
        System.out.println(b.getClass());
    }
}
