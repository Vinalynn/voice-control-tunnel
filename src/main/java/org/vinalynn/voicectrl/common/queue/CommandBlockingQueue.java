package org.vinalynn.voicectrl.common.queue;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * User: caiwm
 * Date: 13-5-11
 * Time: PM 2:50
 */
public class CommandBlockingQueue {

    private transient static Logger log = Logger.getLogger(CommandBlockingQueue.class);

    private static BlockingQueue<Object> bq;
    private static Boolean isInit = Boolean.FALSE;

    static{
        init();
    }

    private static void init(){
        if(!isInit){
            synchronized (isInit){
                bq = new LinkedBlockingDeque<Object>();
                isInit = Boolean.TRUE;
            }
        }

    }


    public static void pushOneCommand(Object obj) throws Exception{
        if(bq.remainingCapacity() > 0){
            bq.put(obj);
        }else{
            log.error("due to command blocking queue has no capacity, save command to db.");
            //save to db
            //throw new Exception("");
        }
    }

    public static List<Object> pollAllRemainingCommands() throws Exception{
        List<Object> commands = new ArrayList<Object>();
        while(!bq.isEmpty() && commands.size() < 100){
          commands.add(bq.poll());
        }
        return commands;
    }

}
