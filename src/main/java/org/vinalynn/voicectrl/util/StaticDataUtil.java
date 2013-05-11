package org.vinalynn.voicectrl.util;

import org.vinalynn.voicectrl.common.cache.CacheManager;
import org.vinalynn.voicectrl.common.cache.impl.StaticConfigDataCacheImpl;
import org.vinalynn.voicectrl.doo.StaticConfigDataDO;

import java.util.HashMap;

/**
 * User: caiwm
 * Date: 13-5-11
 * Time: AM 1:04
 */
public class StaticDataUtil {

    /**
     * 根据组别获取BeanStaticData
     *
     * @param uGroup
     * @return
     */
    @SuppressWarnings("unchecked")
	public static HashMap<String, StaticConfigDataDO> getStaticDataByGroup(String uGroup) {
        return (HashMap<String, StaticConfigDataDO>)CacheManager.getCache(StaticConfigDataCacheImpl.class).get(uGroup);
    }

    /**
     * 根据组别和名称获取BeanStaticData
     *
     * @param uGroup
     * @param name
     * @return
     */
    public static StaticConfigDataDO getBeanStaticData(String uGroup, String name) {
        HashMap<String, StaticConfigDataDO> map = getStaticDataByGroup(uGroup);
        if (map != null) {
            return map.get(name);
        }
        return null;
    }
}
