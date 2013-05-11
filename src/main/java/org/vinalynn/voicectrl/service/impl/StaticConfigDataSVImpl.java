package org.vinalynn.voicectrl.service.impl;

import org.vinalynn.voicectrl.doo.StaticConfigDataDO;
import org.vinalynn.voicectrl.mapper.StaticConfigDataMapper;
import org.vinalynn.voicectrl.service.interfaces.StaticConfigDataSV;

import java.util.HashMap;
import java.util.List;

/**
 * User: caiwm
 * Date: 13-5-11
 * Time: 下午12:28
 */
public class StaticConfigDataSVImpl implements StaticConfigDataSV {

    private StaticConfigDataMapper<StaticConfigDataDO> staticConfigDataMapper;

    public void setStaticConfigDataMapper(StaticConfigDataMapper<StaticConfigDataDO> staticConfigDataMapper) {
        this.staticConfigDataMapper = staticConfigDataMapper;
    }

    @SuppressWarnings("rawtypes")
	@Override
    public List<StaticConfigDataDO> getStaticConfigData() throws Exception {
        // query all config data
        return staticConfigDataMapper.findObjectByParam(new HashMap());
    }
}
