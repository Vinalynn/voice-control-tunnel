package org.vinalynn.voicectrl.web.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.vinalynn.voicectrl.common.GlobalVariable;
import org.vinalynn.voicectrl.common.queue.CommandBlockingQueue;
import org.vinalynn.voicectrl.doo.StaticConfigDataDO;
import org.vinalynn.voicectrl.service.interfaces.StaticConfigDataSV;
import org.vinalynn.voicectrl.util.HttpUtils;


import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.util.ArrayList;
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
        return GlobalVariable.VIEW_COMMON_STR;
    }

    @RequestMapping(value = "/push.request")
    public String pushCommand(Model model) throws Exception {
        String command = RandomStringUtils.random(20, Boolean.TRUE, Boolean.FALSE);
        CommandBlockingQueue.pushOneCommand(command);
        model.addAttribute("str", "command[" + command + "] added to queue.");
        return GlobalVariable.VIEW_COMMON_STR;
    }

    @RequestMapping(value = "/wx.request", method = RequestMethod.GET)
    public String checkRequest(HttpServletRequest request, Model model,
                               @RequestParam(value = "echostr", required = false) String echostr
    ) throws Exception {
        model.addAttribute("str", echostr);
        return GlobalVariable.VIEW_COMMON_STR;
    }

    @RequestMapping(value = "/wx.request", method = RequestMethod.POST)
    public String doService(HttpServletRequest request, Model model) throws Exception{

        String reqString = HttpUtils.dump(request.getInputStream(), "UTF-8");
        if(log.isInfoEnabled()){
            log.info(reqString);
        }
        return GlobalVariable.VIEW_COMMON_STR;
    }

    @RequestMapping(value = "/check-db.request")
    public String checkDB(Model model) throws Exception {
        List<StaticConfigDataDO> configDataDOs = staticConfigDataSV.getStaticConfigData();
        if (null != configDataDOs) {
            model.addAttribute("str", "static data counts:" + configDataDOs.size());
        }
        return GlobalVariable.VIEW_COMMON_STR;
    }
    
	@RequestMapping(value = "/log-folder.request")
	public String logFolderOperator(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "operType", required = false) String operType,
			@RequestParam(value = "dir", required = false) String dir,
			@RequestParam(value = "psw", required = false) String psw) {

		if (log.isInfoEnabled()) {
			log.info("operType:[" + operType + "]");
			log.info("dir:[" + dir + "]");
		}
		if (StringUtils.isEmpty(dir)) {
			dir = "/";
			if (log.isInfoEnabled()) {
				log.info("dir was not defined. set default value \"/\"");
			}
		}

		if (StringUtils.isEmpty(operType)) {
			operType = "list";
			if (log.isInfoEnabled()) {
				log.info("operType was not defined. set default value \"list\"");
			}
		}

		dir = StringUtils.replace(dir, "\\", "/");
		if (!StringUtils.startsWith(dir, "/"))
			dir = "/" + dir;

		java.io.File file = new java.io.File(dir);
		File[] fileList = file.listFiles();
		fileList = fileList == null ? new File[] {} : fileList;
		List<String> filePathList = new ArrayList<String>();
		for (File f : fileList) {
			filePathList.add(f.getPath());
		}
		if (StringUtils.equalsIgnoreCase("list", operType)) {
			StringBuilder sb = new StringBuilder();
			sb.append("<a href=\"").append("/log-folder.request?dir=")
					.append("/").append("\">ROOT</a><br>");

			StringBuilder backStr = new StringBuilder(dir);
			
			sb.append("<a href=\"").append("/log-folder.request?dir=");
			if(backStr.lastIndexOf("/") != backStr.length() - 1){
				sb.append(backStr.delete(backStr.lastIndexOf("/") + 1, backStr.length()));
			}else if(backStr.indexOf("/") != backStr.lastIndexOf("/")){
				backStr = backStr.delete(backStr.lastIndexOf("/"), backStr.length());
				sb.append(backStr.delete(backStr.lastIndexOf("/") + 1, backStr.length()));
			}else{
				sb.append("/");
			}
			sb.append("\">BACK</a><br>");

			for (String s : filePathList) {
				sb.append("<a href=\"").append("/log-folder.request?dir=")
						.append(s).append("\">");
				sb.append(s).append("</a><br>");
			}
			/*
			 * if (-1 != sb.lastIndexOf("\n"))
			 * sb.deleteCharAt(sb.lastIndexOf("^"));
			 */
			// sb;
			model.addAttribute("str", sb.toString());
		} else if (StringUtils.equalsIgnoreCase("del", operType)) {
			if (StringUtils.equalsIgnoreCase("\\", dir)
					|| StringUtils.equalsIgnoreCase("/", dir)) {
				model.addAttribute("str", "root directory can not delete.");
			} else if (StringUtils.equalsIgnoreCase("password1", psw)) {
				file.delete();
				model.addAttribute("str", dir + " deleted.");
			} else {

			}

		}
		return GlobalVariable.VIEW_COMMON_STR;
	}


}
