package org.vinalynn.voicectrl.service;

import junit.framework.TestCase;
import org.junit.Test;
import org.vinalynn.voicectrl.common.AppContextFactory;
import org.vinalynn.voicectrl.doo.VoiceControlUserDO;
import org.vinalynn.voicectrl.service.interfaces.VoiceControlUserSV;

/**
 * User: caiwm
 * Date: 13-5-10
 * Time: AM 3:55
 */
public class TestService {


    /**
     * check database linkable
     *
     * @throws Exception
     */
    @Test
    public void testVoiceControlUserSV() throws Exception{
        VoiceControlUserSV voiceControlUserSV = AppContextFactory.getBean(VoiceControlUserSV.class);
        VoiceControlUserDO user = voiceControlUserSV.findUserById(12);

        TestCase.assertEquals(null, user);
    }


}
