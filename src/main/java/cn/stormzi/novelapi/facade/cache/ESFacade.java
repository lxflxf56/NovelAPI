package cn.stormzi.novelapi.facade.cache;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ESFacade {

    boolean exist(String index, String type, long id) throws IOException;
    String select(String index, String type, long id) throws IOException;
    String select(String index, String type, String json) throws IOException;
    void bulk(String json);
    void bulk(List list);
    void insert(String index, String type, long id, Map par);
    void insert(String index, String type, long id, String json);
    void delect(String index, String type, long id, Map par);
    void delect(String index, String type, long id);
    void update(String index, String type, long id, Map par);
    void update(String index, String type, long id, String json);
    void updateAll(String index, String type, long id, String json);
}
