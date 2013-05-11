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
    private VoiceControlUserMapper<VoiceControlUserDO> voiceControlUserMapper;

	public void setVoiceControlUserMapper(
			VoiceControlUserMapper<VoiceControlUserDO> voiceControlUserMapper) {
		this.voiceControlUserMapper = voiceControlUserMapper;
	}


    @Override
    public VoiceControlUserDO findUserById(int id) throws Exception {
        return (VoiceControlUserDO) voiceControlUserMapper.findById(id);
    }
}
