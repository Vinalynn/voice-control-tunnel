package org.vinalynn.voicectrl.common.cache.impl;

import org.apache.log4j.Logger;
import org.vinalynn.voicectrl.common.cache.ICache;
import org.vinalynn.voicectrl.doo.StaticConfigDataDO;
import org.vinalynn.voicectrl.service.interfaces.StaticConfigDataSV;

import java.util.HashMap;
import java.util.List;

/**
 * User: caiwm
 * Date: 13-5-11
 * Time: 下午12:37
 */
public class StaticConfigDataCacheImpl extends ICache {

    private StaticConfigDataSV staticConfigDataSV;

    public void setStaticConfigDataSV(StaticConfigDataSV staticConfigDataSV) {
        this.staticConfigDataSV = staticConfigDataSV;
    }

    private transient static Logger log =
            Logger.getLogger(StaticConfigDataCacheImpl.class);

    @Override
    protected HashMap getData() {
        HashMap<String, HashMap<String, StaticConfigDataDO>> data =
                new HashMap<String, HashMap<String, StaticConfigDataDO>>();
        try{
            //StaticConfigDataSV staticConfigDataSV = AppContextFactory.getBean(StaticConfigDataSV.class);
            List<StaticConfigDataDO> list = staticConfigDataSV.getStaticConfigData();
            HashMap<String, StaticConfigDataDO> temp;
            for(StaticConfigDataDO d : list){
                if(data.containsKey(d.getGrp())){
                    temp = data.get(d.getGrp());
                }else{
                    temp = new HashMap<String, StaticConfigDataDO>();
                }
                temp.put(d.getName(), d);
                data.put(d.getGrp(), temp);
            }

        } catch (Exception e){
            e.printStackTrace();
            log.error("cache [StaticConfigDataCacheImpl] load failed.");

        }
        return data;
    }
}
