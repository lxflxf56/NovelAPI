package cn.stormzi.novelapi.util.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @program: novelapi
 * @description:
 * @author: Xiaofeng
 * @create: 2019-01-02
 **/


public class ESQueryBuilder {


    /**
     * query : {"match_phrase":{"address":"mill lane"},"multi_match":{"query":"jumping rabbits","type":"most_fields","fields":["title","title.std"]},"match":{"title":"QUICK!"},"bool":{"should":[{"term":{"price":20}},{"term":{"productID":"XHDK-A-1293-#fJ3"}}],"must_not":{"term":{"price":30,"price2":[20,30]}},"must":[{"match":{"address":"mill"}},{"match":{"address":"lane"}}]},"constant_score":{"filter":{"term":{"price":20},"range":{"price":{"gte":20,"lt":40}},"exists":{"field":"tags"},"missing":{"field":"tags"}}}}
     * sort : {"balance":{"order":"desc"}}
     * from : 10
     * size : 10
     * _source : ["account_number","balance"]
     * aggs : {"group_by_state":{"terms":{"field":"state"}},"average_balance":{"avg":{"field":"balance"}}}
     */

    public static ESQueryBuilder newInstance() {
        return new ESQueryBuilder();
    }




    public ESQueryBuilder term(String field, Object value) {
        if (this.query.constant_score == null) {
            this.query.constant_score = new QueryBean.ConstantScoreBean();
        }
        if (this.query.constant_score.filter.term == null) {
            this.query.constant_score.filter.term = new JSONObject();
        }
        this.query.constant_score.filter.term.put(field, value);
        return this;
    }


    public ESQueryBuilder match(String field, Object value){
        if (query.match==null){
            query.match=new JSONObject();
        }
        query.match.put(field,value);
        return this;
    }

    /**
    gt: > 大于（greater than）
    lt: < 小于（less than）
    gte: >= 大于或等于（greater than or equal to）
    lte: <= 小于或等于（less than or equal to）
    * */
    public ESQueryBuilder RangeByconstantScore(String key, String operate, Object value) {
        if (this.query.constant_score == null) {
            this.query.constant_score = new QueryBean.ConstantScoreBean();
        }
        this.query.constant_score.RangeByconstantScore(key, operate, value);
        return this;
    }

    public ESQueryBuilder must(JSONObject jsonObject) {
        if (query.bool == null) {
            query.bool = new QueryBean.BoolBean();
        }
        query.bool.must = jsonObject;
        return this;
    }

    public ESQueryBuilder must_not(JSONObject jsonObject) {
        if (query.bool == null) {
            query.bool = new QueryBean.BoolBean();
        }
        query.bool.must_not = jsonObject;
        return this;
    }

    public ESQueryBuilder should(JSONObject jsonObject) {
        if (query.bool == null) {
            query.bool = new QueryBean.BoolBean();
        }
        query.bool.should = jsonObject;
        return this;
    }



    private QueryBean query = new QueryBean();
    private JSONObject sort;
    private int from;
    private int size=10;
    private JSONObject aggs;
    private List<String> _source;

    public static class QueryBean {
        /**
         * match_phrase : {"address":"mill lane"}
         * multi_match : {"query":"jumping rabbits","type":"most_fields","fields":["title","title.std"]}
         * match : {"title":"QUICK!"}
         * bool : {"should":[{"term":{"price":20}},{"term":{"productID":"XHDK-A-1293-#fJ3"}}],"must_not":{"term":{"price":30,"price2":[20,30]}},"must":[{"match":{"address":"mill"}},{"match":{"address":"lane"}}]}
         * constant_score : {"filter":{"term":{"price":20},"range":{"price":{"gte":20,"lt":40}},"exists":{"field":"tags"},"missing":{"field":"tags"}}}
         */

        JSONObject match_phrase;
        MultiMatchBean multi_match;
        JSONObject match;
        BoolBean bool;
        ConstantScoreBean constant_score;


        public static class MultiMatchBean {
            /**
             * query : jumping rabbits
             * type : most_fields
             * fields : ["title","title.std"]
             */

            String query;
            String type;
            List<String> fields;


            public String getQuery() {
                return query;
            }

            public String getType() {
                return type;
            }

            public List<String> getFields() {
                return fields;
            }
        }


        public static class BoolBean {
            /**
             * should : [{"term":{"price":20}},{"term":{"productID":"XHDK-A-1293-#fJ3"}}]
             * must_not : {"term":{"price":30,"price2":[20,30]}}
             * must : [{"match":{"address":"mill"}},{"match":{"address":"lane"}}]
             */
            JSONObject must_not;
            JSONObject should;
            JSONObject must;

            public JSONObject getMust_not() {
                return must_not;
            }

            public JSONObject getShould() {
                return should;
            }

            public JSONObject getMust() {
                return must;
            }
        }

        public static class ConstantScoreBean {
            /**
             * filter : {"term":{"price":20},"range":{"price":{"gte":20,"lt":40}},"exists":{"field":"tags"},"missing":{"field":"tags"}}
             */

            public void RangeByconstantScore(String key, String operate, Object value) {
                if (filter.range == null) {
                    filter.range = new JSONObject();
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(operate, value);
                filter.range.put(key, jsonObject);
            }

            FilterBean filter = new FilterBean();

            public static class FilterBean {
                /**
                 * term : {"price":20}
                 * range : {"price":{"gte":20,"lt":40}}
                 * exists : {"field":"tags"}
                 * missing : {"field":"tags"}
                 */

                JSONObject term;
                JSONObject range;
                JSONObject exists;
                JSONObject missing;

                public JSONObject getTerm() {
                    return term;
                }

                public JSONObject getRange() {
                    return range;
                }

                public JSONObject getExists() {
                    return exists;
                }

                public JSONObject getMissing() {
                    return missing;
                }
            }
        }

        public JSONObject getMatch_phrase() {
            return match_phrase;
        }

        public MultiMatchBean getMulti_match() {
            return multi_match;
        }

        public JSONObject getMatch() {
            return match;
        }

        public BoolBean getBool() {
            return bool;
        }

        public ConstantScoreBean getConstant_score() {
            return constant_score;
        }
    }



    public void setFrom(int from) {
        this.from = from;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setSort(JSONObject sort) {
        this.sort = sort;
    }

    public void setAggs(JSONObject aggs) {
        this.aggs = aggs;
    }

    public void set_source(List<String> _source) {
        this._source = _source;
    }

    public String toJSONString(){
        return JSON.toJSONString(this);
    }

    public QueryBean getQuery() {
        return query;
    }

    public JSONObject getSort() {
        return sort;
    }

    public int getFrom() {
        return from;
    }

    public int getSize() {
        return size;
    }

    public JSONObject getAggs() {
        return aggs;
    }

    public List<String> get_source() {
        return _source;
    }
}
