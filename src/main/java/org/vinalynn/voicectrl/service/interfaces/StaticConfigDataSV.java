package org.vinalynn.voicectrl.service.interfaces;

import org.vinalynn.voicectrl.doo.StaticConfigDataDO;

import java.util.List;

/**
 * User: caiwm
 * Date: 13-5-11
 * Time: 下午12:27
 */
public interface StaticConfigDataSV {

    /**
     *
     * @return
     * @throws Exception
     */
    List<StaticConfigDataDO> getStaticConfigData() throws Exception;
}
