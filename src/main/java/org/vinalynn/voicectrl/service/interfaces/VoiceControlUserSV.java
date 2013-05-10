package org.vinalynn.voicectrl.service.interfaces;

import org.vinalynn.voicectrl.doo.VoiceControlUserDO;

/**
 * User: caiwm
 * Date: 13-5-10
 * Time: 下午3:49
 */
public interface VoiceControlUserSV {


    /**
     * query voice_control user by id
     *
     * @param id            userId
     * @return              VoiceControlUserDO
     * @throws Exception
     */
    VoiceControlUserDO findUserById(int id) throws Exception;
}
