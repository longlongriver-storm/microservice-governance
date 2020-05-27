package com.storm.monitor.dashboard.controller;

import com.alibaba.fastjson.JSON;
import com.storm.monitor.server.model.CustomDataPickLog;
import com.storm.monitor.server.model.view.CustomDataPickLogView;
import com.storm.monitor.server.service.CustomDataPickLogService;
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
@RequestMapping("/dashboard_customdatapicklog")
public class CustomDataPickLogController {

    @Autowired
    private CustomDataPickLogService customDataPickLogService;

    /**
     * 跳转到【业务数据采集汇总表】信息列表页面
     *
     * @param request
     * @return
     */
    @RequestMapping("gotocustomdatapicklog")
    public String gotoCustomDataPickLog(HttpServletRequest request) {
        return "dashboard/customdatapicklog_list";
    }

    /**
     * 查询列表数据
     *
     * @param customDataPickLogView
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping({"/querycustomdatapicklog"})
    @ResponseBody
    public List<CustomDataPickLog> queryCustomDataPickLog(CustomDataPickLogView customDataPickLogView, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println(JSON.toJSONString(customDataPickLogView));
        List<CustomDataPickLog> list = new ArrayList();
        if (customDataPickLogView != null) {
            list = this.customDataPickLogService.queryCustomDataPickLogByPage(customDataPickLogView);
        }

        return list;
    }

    /**
     * 查询列表数据
     *
     * @param customDataPickLogView
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping({"/querycustomdatapicklogpage"})
    @ResponseBody
    public Map queryCustomDataPickLogPage(CustomDataPickLogView customDataPickLogView, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List<CustomDataPickLog> list = queryCustomDataPickLog(customDataPickLogView, request, response);
        int resultCount = (customDataPickLogView == null) ? 0 : this.customDataPickLogService.queryCustomDataPickLogByCount(customDataPickLogView);

        Map result = new HashMap();
        result.put("rows", list);
        result.put("total", resultCount);
        return result;
    }

    /**
     * 打开【业务数据采集汇总表】信息编辑（新增）页面
     *
     * @param customDataPickLog
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/gotocustomdatapicklogedit")
    public String gotoCustomDataPickLogEdit(CustomDataPickLog customDataPickLog, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(JSON.toJSONString(customDataPickLog));
        Long id = customDataPickLog.getId();
        if (id != null && !"".equals(id)) {
            customDataPickLog = this.customDataPickLogService.queryCustomDataPickLogById(id);

        }
        request.setAttribute("pj", customDataPickLog);

        return "dashboard/customdatapicklog_add";
    }

    /**
     * 新增或者删除一条记录
     *
     * @param customDataPickLog
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping({"/addcustomdatapicklog"})
    @ResponseBody
    public CustomDataPickLog addCustomDataPickLog(CustomDataPickLog customDataPickLog, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println(JSON.toJSONString(customDataPickLog));
        CustomDataPickLog oldCfg = null;

        if (null != customDataPickLog.getId()) {
            oldCfg = this.customDataPickLogService.queryCustomDataPickLogById(customDataPickLog.getId());
        }

        if (oldCfg == null) {   //新增
            this.customDataPickLogService.addCustomDataPickLog(customDataPickLog);
        } else {              //更新
            this.customDataPickLogService.updateCustomDataPickLog(customDataPickLog);
        }

        return customDataPickLog;
    }

}
