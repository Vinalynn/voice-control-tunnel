package org.vinalynn.voicectrl.web.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.vinalynn.voicectrl.common.queue.CommandBlockingQueue;
import org.vinalynn.voicectrl.doo.StaticConfigDataDO;
import org.vinalynn.voicectrl.service.interfaces.StaticConfigDataSV;
import org.vinalynn.voicectrl.util.HttpUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * User: caiwm
 * Date: 13-5-11
 * Time: AM 9:13
 */
@Controller
public class OpenWeixinController {

    private static transient Logger log = Logger.getLogger(OpenWeixinController.class);

    private StaticConfigDataSV staticConfigDataSV;

    public void setStaticConfigDataSV(StaticConfigDataSV staticConfigDataSV) {
        this.staticConfigDataSV = staticConfigDataSV;
    }

    @RequestMapping(value = "/test.request")
    public String test(Model model) throws Exception {
        model.addAttribute("str", "Hello World!");
        return "common/str";
    }

    @RequestMapping(value = "/push.request")
    public String pushCommand(Model model) throws Exception {
        String command = RandomStringUtils.random(20, Boolean.TRUE, Boolean.FALSE);
        CommandBlockingQueue.pushOneCommand(command);
        model.addAttribute("str", "command[" + command + "] added to queue.");
        return "common/str";
    }

    @RequestMapping(value = "/wx.request", method = RequestMethod.GET)
    public String checkRequest(HttpServletRequest request, Model model,
                               @RequestParam(value = "echostr", required = false) String echostr
    ) throws Exception {
        model.addAttribute("str", echostr);
        return "common/str";
    }

    @RequestMapping(value = "/wx.request", method = RequestMethod.POST)
    public String doService(HttpServletRequest request, Model model) throws Exception{

        String reqString = HttpUtils.dump(request.getInputStream(), "UTF-8");
        if(log.isInfoEnabled()){
            log.info(reqString);
        }
        return "common/str";
    }

    @RequestMapping(value = "/check-db.request")
    public String checkDB(Model model) throws Exception {
        List<StaticConfigDataDO> configDataDOs = staticConfigDataSV.getStaticConfigData();
        if (null != configDataDOs) {
            model.addAttribute("str", "static data counts:" + configDataDOs.size());
        }
        return "common/str";
    }


}
