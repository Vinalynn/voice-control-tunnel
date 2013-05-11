package org.vinalynn.voicectrl.job;

import org.apache.log4j.Logger;
import org.vinalynn.voicectrl.common.queue.CommandBlockingQueue;
import org.vinalynn.voicectrl.doo.StaticConfigDataDO;
import org.vinalynn.voicectrl.util.StaticDataUtil;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

/**
 * User: caiwm
 * Date: 13-5-11
 * Time: 上午10:55
 */
public class CommandPersistenceJob {

    private transient static Logger log = Logger.getLogger(CommandPersistenceJob.class);

    public void persistence() throws Exception{
        //HashMap<String, StaticConfigDataDO> uGrpDatas = StaticDataUtil.getStaticDataByGroup("11");
        if(log.isInfoEnabled()){
            log.info("start do job at "+ new Timestamp(System.currentTimeMillis()));

        }

        List<Object> commands = CommandBlockingQueue.pollAllRemainingCommands();
        log.error(commands);
//        if(null != uGrpDatas){
//            if(log.isInfoEnabled()){
//                log.info("cached data size :"+uGrpDatas.size());
//            }
//        }else{
//            if(log.isInfoEnabled()){
//                log.info("cached data size : 0");
//            }
//        }
        //System.out.println("start do job at "+ new Date(System.currentTimeMillis()));
    }

}
