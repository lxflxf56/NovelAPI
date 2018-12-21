package cn.stormzi.novelapi.service;

import cn.stormzi.novelapi.util.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-19
 **/

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class SearchServiceTest {

    public void sear()  {
        String bookname = "如意";
        String gbk = null;
        try {
            gbk = URLEncoder.encode(bookname, "gbk");
            String searchUrl = String.format("https://www.i7wx.com/modules/article/search.php?searchtype=articlename&searchkey=%s", gbk);
            System.out.println(searchUrl);
            Document document = Jsoup.connect(searchUrl).post();
            String s1 = HttpUtil.get(searchUrl);
            //Document document = Jsoup.parse(s1,"gbk");
            String[] s = "@tr .odd 1 ".split(" ");
            List result = new ArrayList();
            itrElement(0, s, document.body().children(), result);
            System.out.println(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void itrElement(int i, String[] s, Elements element, List result) {
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
            Element element1 = element.get(str.charAt(0));
            if (str.length() > 1) {// 0[attr]
                str = str.substring(1, str.length());
                String attr = element1.attr(str);
                result.add(attr);
            }else {
                result.add(element1.html());
            }
        } else if (str.charAt(0) == '[') {
            String attr = element.attr(str);
            result.add(attr);
        } else if (str.charAt(0) == '@') {
            str = str.substring(1, str.length());
            Elements select = element.select(str);
            for (Element element1 : select) {
                itrElement(i + 1, s, element1.children(), result);
            }
        } else {
            itrElement(i + 1, s, element.select(str), result);
        }

    }


    public static boolean isNum(char c) {
        if (47 < c && c < 57) {
            return true;
        }
        return false;
    }


    public static String change(String tmp) {
        String s = tmp;
        if (s == null || s.length() == 0) {
            return "";
        }
        String pattern1 = "@(\\d+)@";
        Pattern r = Pattern.compile(pattern1);
        s = s.replace("@@@", "unknow ")
                .replace("@td", " tr")
                .replace("@div", "").replace("@span", "");
        Matcher m = r.matcher(s);
        if (m.find()) {
            //System.out.println("Found value: " + m.group(1) );
            s = s.replaceAll("@" + m.group(1) + "@", " " + m.group(1) + "");
        }
        //s=s.replaceAll("@","");
        if (s.indexOf("abs:") >= 0) {
            s = s.replace("abs:", "[") + "]";
        }
        s = s.replace(".", " .").replace("#", " #");
        s = s.replace("@", "");
        return s.trim();
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            builder.append(s);
            if (s.length() == 0) {
                break;
            }
        }

        String json = builder.toString();
        List<Map> maps = JSON.parseArray(json, Map.class);
        for (Map map : maps) {
            Iterator<String> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                String s = iterator.next();
                if (s.indexOf("host") >= 0 || s.indexOf("searchUrl") >= 0 || s.indexOf("Replace") >= 0) {
                    continue;
                }
                String s1 = map.get(s).toString();
                String change = change(s1);
                map.put(s, change);
            }
        }

        System.out.println(JSON.toJSONString(maps));
    }
}
