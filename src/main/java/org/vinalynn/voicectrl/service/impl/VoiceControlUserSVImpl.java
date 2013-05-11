package org.vinalynn.voicectrl.service.impl;

import org.vinalynn.voicectrl.doo.VoiceControlUserDO;
import org.vinalynn.voicectrl.mapper.VoiceControlUserMapper;
import org.vinalynn.voicectrl.service.interfaces.VoiceControlUserSV;

/**
 * User: caiwm
 * Date: 13-5-10
 * Time: 下午3:52
 */
public class VoiceControlUserSVImpl implements VoiceControlUserSV {
    private VoiceControlUserMapper voiceControlUserMapper;

    public void setVoiceControlUserMapper(VoiceControlUserMapper voiceControlUserMapper) {
        this.voiceControlUserMapper = voiceControlUserMapper;
    }


    @Override
    public VoiceControlUserDO findUserById(int id) throws Exception {
        return (VoiceControlUserDO) voiceControlUserMapper.findById(id);
    }
}
