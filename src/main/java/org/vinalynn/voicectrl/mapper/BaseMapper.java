package org.vinalynn.voicectrl.mapper;

import java.util.List;
import java.util.Map;

/**
 * User: caiwm
 * Date: 13-5-10
 * Time: 下午3:37
 */
public interface BaseMapper<T> {

    List<T> findObjectByParam(Map map);
    void update(T t);
    int save(T t);
    T findById(Integer id);
    void delete(T t);

}
