package cn.stormzi.novelapi.util.elasticsearch;


import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2018-12-13
 **/


public class QueryBuilder {

    
    /**
    * @Description: 精确值查询
    * @Author: Xiaofeng
    * @Date: 2018/12/19 
    */ 
    public static String term(String key,String value){
        JSONObject json=new JSONObject();
        JSONObject query=new JSONObject();
        JSONObject constant_score=new JSONObject();
        JSONObject filter=new JSONObject();
        JSONObject term=new JSONObject();
        {
            json.put("query",query);
            {
                query.put("constant_score",constant_score);
                {
                    constant_score.put("filter",filter);
                    {
                        filter.put("term",term);
                        {
                            term.put(key,value);
                        }
                    }
                }
            }
        }
        return json.toJSONString();
    }


    
    /**
    * @Description: 相似查询 
    * @Author: Xiaofeng
    * @Date: 2018/12/19 
    */ 
    public static String similarQuery(String key,String value){
        return "";
    }

    public static String term(List<NodeParent> value){
        StringBuilder builder=new StringBuilder();
        builder.append("{" +
                "    \"query\" : {" +
                "        \"constant_score\" : { " +
                "            \"filter\" : {" +
                "                \"term\" :");
        builder.append(value.toString());
        builder.append("}" +
                "        }" +
                "    }" +
                "}");
        return builder.toString();
    }


    public static String term(String key,Object value){
        String json="{\n" +
                "    \"query\" : {\n" +
                "        \"constant_score\" : { \n" +
                "            \"filter\" : {\n" +
                "                \"term\" : { \n" +
                "                    \""+key+"\" : "+value.toString()+"\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";
        return json;
    }


    public static class NodeParent implements Serializable {
        String name;
        List<NodeParent> nodeParentList = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();


        public NodeParent(String name,String key,Object value){
            Node node = new Node(key, value);
            this.name=name;
            nodes.add(node);
        }

        public NodeParent addNode(Node node) {
            nodes.add(node);
            return this;
        }

        public NodeParent addNode(NodeParent node) {
            nodeParentList.add(node);
            return this;
        }

        public NodeParent(String name) {
            this.name = name;
        }

        public NodeParent() {
            this.name = "";
        }


        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("{ ");

            for (NodeParent nodeParent : nodeParentList) {
                builder.append('"').append(nodeParent.getName()).append('"').append(" : ");
                builder.append(nodeParent.toString()).append(",");
            }


            if (nodes.isEmpty()){
                builder.deleteCharAt(builder.length()-1);
            }
            for (Node node : nodes) {
                builder.append(node.toString()).append(",");
            }
            if (!nodes.isEmpty()){
                builder.deleteCharAt(builder.length()-1);
            }



            builder.append(" }");
            return builder.toString();

        }
    }

    public static class Node implements Serializable {
        String key;
        List list;
        String string;
        Object value;

        public Node(String key, String string) {
            this.key = key;
            this.string = string;
        }

        public Node(String key, List list) {
            this.key = key;
            this.list = list;
        }

        public Node(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append('"').append(key).append('"').append(" : ");
            if (string != null) {
                builder.append('"').append(string).append('"');
            } else if (value != null) {
                builder.append(value);
            } else if (list != null) {
                builder.append(list.toString());
            }
            return builder.toString();
        }
    }
}
