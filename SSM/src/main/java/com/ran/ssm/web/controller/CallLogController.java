package com.ran.ssm.web.controller;

import com.alibaba.fastjson.JSON;
import com.ran.ssm.domain.CallLog;
import com.ran.ssm.domain.CallLogRange;
import com.ran.ssm.domain.CallLogStat;
import com.ran.ssm.hive.HiveCallLogService;
import com.ran.ssm.service.CallLogService;
import com.ran.ssm.util.CallLogUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2017/4/11.
 */
@Controller
public class CallLogController {

    //注入hiveservice
    @Resource(name="hiveCallLogService")
    private HiveCallLogService hcs ;

    @Resource(name="callLogService")
    private CallLogService cs ;

    @RequestMapping("/callLog/findAll")
    public String findAll(Model m){
        List<CallLog> list = cs.findAll();
        m.addAttribute("callLogs",list);
        return "callLog/callLogList" ;
    }

    @RequestMapping("/callLog/json/findAll")
    public String findAllJson(HttpServletResponse response) {//服务器给出响应
        List<CallLog> list = cs.findAll();
        String json = JSON.toJSONString(list);
        //服务器端告诉客户端浏览器回传的内容类型
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try {
            OutputStream out = response.getOutputStream();
            out.write(json.getBytes());
            /**** json文件采用编码的字符集 ****/
            out.write(json.getBytes("utf-8"));
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 进入查询通话记录的页面,form
     */
    @RequestMapping("/callLog/toFindCallLogPage")
    public String toFindCallLogPage(){
        return "callLog/findCallLog" ;
    }

    @RequestMapping(value = "/callLog/findCallLog",method = RequestMethod.POST)
    public String findCallLog(Model m , @RequestParam("caller") String caller, @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime){
        List<CallLogRange> list = CallLogUtil.getCallLogRanges(startTime, endTime);
        List<CallLog> logs = cs.findCallogs(caller,list);
        m.addAttribute("callLogs", logs);
        return "callLog/callLogList" ;
    }


    /**
     * 利用hive来查询最近通话记录
     */
    @RequestMapping(value = "/callLog/findLatestCallLog",method = RequestMethod.POST)
    public String findLatestCallLog(Model m , @RequestParam("caller") String caller){
        CallLog log = hcs.findLatestCallLog(caller);
        if(log != null){
            m.addAttribute("log", log);
        }
        return "callLog/latestCallLog" ;
    }

    /**
     * 查询最近通话记录
     */
    @RequestMapping(value = "/callLog/toFindLatestCallLog")
    public String toFindLatestCallLog(){
        return "callLog/findLatestCallLog" ;
    }



    /**
     * 统计指定人员，指定月份的通话次数
     */
    @RequestMapping("/callLog/toStatCallLog")
    public String toStatCallLog(){
        return "callLog/statCallLog" ;
    }
    /**
     * 统计指定人员，指定月份的通话次数
     */
    @RequestMapping("/callLog/statCallLog")
    public String statCallLog(Model m ,@RequestParam("caller") String caller ,@RequestParam("year") String year){
        List<CallLogStat> list = hcs.statCallLogsCount(caller, year);

        if(list != null && !list.isEmpty()){
            List<String> months = new ArrayList<String>();
            List<Integer> counts = new ArrayList<Integer>();
            for(CallLogStat cls : list){
                months.add(cls.getYearMonth());
                counts.add(cls.getCount());
            }

            m.addAttribute("title", caller + "在" + year + "年各月份通话次数统计");
            m.addAttribute("list", list);
        }

        return "callLog/statCallLog" ;
    }

}