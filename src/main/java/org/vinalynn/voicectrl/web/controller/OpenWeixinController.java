package org.vinalynn.voicectrl.web.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.vinalynn.voicectrl.common.queue.CommandBlockingQueue;

import javax.servlet.http.HttpServletRequest;

/**
 * User: caiwm
 * Date: 13-5-11
 * Time: AM 9:13
 */
@Controller
public class OpenWeixinController {

    @RequestMapping(value = "/test.request")
    public String test(Model model) throws Exception{
        model.addAttribute("str", "Hello World!");
        return "common/str";
    }

    @RequestMapping(value = "/push.request")
    public String pushCommand(Model model) throws Exception{
        String command = RandomStringUtils.random(20, Boolean.TRUE, Boolean.FALSE);
        CommandBlockingQueue.pushOneCommand(command);
        model.addAttribute("str", "command[" + command + "] added to queue.");
        return "common/str";
    }

    @RequestMapping(value = "/wx.request")
    public String checkRequest(HttpServletRequest request, Model model,
                               @RequestParam(value = "echostr", required = false) String echostr
                               ) throws Exception{
        model.addAttribute("str", echostr);
        return "common/str";
    }


}
