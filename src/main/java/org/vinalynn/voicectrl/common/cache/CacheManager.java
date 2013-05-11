package org.vinalynn.voicectrl.common.cache;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.vinalynn.voicectrl.common.AppContextAwaredFactory;

import java.util.HashMap;

/**
 * we暂时写个不需要同一加载的，以后改成按需加载
 * User: pecho
 * Date: 4/13/13
 * Time: 3:56 PM
 * E_Mail:rudys.eva@gmail.com
 *
 * @author Qinjin Peng
 * @version 1.0
 */
public class CacheManager {

    private static HashMap<Class, HashMap> cache = new HashMap<Class, HashMap>();

    /**
     * 获取缓存的相关信息
     *
     * @param cacheClass
     * @return
     */
    public static HashMap getCache(Class cacheClass) {
        init(cacheClass);
        return cache.get(cacheClass);
    }

    /**
     * 初始化缓存信息，根据类名
     *
     * @param cacheClass
     */
    private static void init(Class cacheClass) {
        /*if (!cache.containsKey(cacheClass)) {*/
        synchronized (cache) {
                /*if (!cache.containsKey(cacheClass)) {*/
            ICache iCache = null;
            try {
                iCache = (ICache) AppContextAwaredFactory.getBean(cacheClass);
            } catch (Exception e) {
                throw new RuntimeException("instance " + cacheClass.getName() + " failed.");
            }
            cache.put(cacheClass, iCache.getData());
                /*}*/
        }
        /*}*/
    }

    /**
     * 或获取Cache信息根据Key
     *
     * @param cacheClass
     * @param key
     * @return
     */
    public static Object getCache(Class cacheClass, Object key) {
        return getCache(cacheClass).get(key);
    }
}
