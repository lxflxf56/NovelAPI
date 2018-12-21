package cn.stormzi.novelapi.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-18
 **/



//@RunWith(SpringRunner.class)
//@SpringBootTest
public class UrlServiceTest {


    public void url() throws UnsupportedEncodingException {
        String value="如意";
        String encode = URLEncoder.encode(value, "gbk");
        System.out.println(encode);
    }
}
