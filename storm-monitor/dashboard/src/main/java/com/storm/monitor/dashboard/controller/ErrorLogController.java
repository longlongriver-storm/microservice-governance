package com.storm.monitor.dashboard.controller;

import com.alibaba.fastjson.JSON;
import com.storm.monitor.server.model.ErrorLog;
import com.storm.monitor.server.model.view.ErrorLogView;
import com.storm.monitor.server.service.ErrorLogService;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/dashboard_errorlog")
public class ErrorLogController {

    @Autowired
    private ErrorLogService errorLogService;

    /**
     * 跳转到【异常信息汇总表】信息列表页面
     *
     * @param request
     * @return
     */
    @RequestMapping("gotoerrorlog")
    public String gotoErrorLog(HttpServletRequest request) {
        return "dashboard/errorlog_list";
    }

    /**
     * 查询列表数据
     *
     * @param errorLogView
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping({"/queryerrorlog"})
    @ResponseBody
    public List<ErrorLog> queryErrorLog(ErrorLogView errorLogView, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println(JSON.toJSONString(errorLogView));
        List<ErrorLog> list = new ArrayList();
        if (errorLogView != null) {
            list = this.errorLogService.queryErrorLogByPage(errorLogView);
        }

        return list;
    }

    /**
     * 查询列表数据
     *
     * @param errorLogView
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping({"/queryerrorlogpage"})
    @ResponseBody
    public Map queryErrorLogPage(ErrorLogView errorLogView, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List<ErrorLog> list = queryErrorLog(errorLogView, request, response);
        int resultCount = (errorLogView == null) ? 0 : this.errorLogService.queryErrorLogByCount(errorLogView);

        Map result = new HashMap();
        result.put("rows", list);
        result.put("total", resultCount);
        return result;
    }

    /**
     * 打开【异常信息汇总表】信息编辑（新增）页面
     *
     * @param errorLog
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/gotoerrorlogedit")
    public String gotoErrorLogEdit(ErrorLog errorLog, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(JSON.toJSONString(errorLog));
        Long id = errorLog.getId();
        if (id != null && !"".equals(id)) {
            errorLog = this.errorLogService.queryErrorLogById(id);

        }
        request.setAttribute("pj", errorLog);

        return "dashboard/errorlog_add";
    }

    /**
     * 新增或者删除一条记录
     *
     * @param errorLog
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping({"/adderrorlog"})
    @ResponseBody
    public ErrorLog addErrorLog(ErrorLog errorLog, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println(JSON.toJSONString(errorLog));
        ErrorLog oldCfg = null;

        if (null != errorLog.getId()) {
            oldCfg = this.errorLogService.queryErrorLogById(errorLog.getId());
        }

        if (oldCfg == null) {   //新增
            this.errorLogService.addErrorLog(errorLog);
        } else {              //更新
            this.errorLogService.updateErrorLog(errorLog);
        }

        return errorLog;
    }

    /**
     * 删除配置
     *
     * @param errorLog
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping({"/deleteerrorlog"})
    @ResponseBody
    public boolean deleteErrorLog(ErrorLog errorLog, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println(JSON.toJSONString(errorLog));
        Long id = errorLog.getId();
        System.out.println(id);
        int count = this.errorLogService.deleteErrorLogById(id);

        return count > 0;

    }

}
