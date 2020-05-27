package com.storm.monitor.dashboard.controller;

import org.apache.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.storm.monitor.core.util.UUIDGenerator;
import com.storm.monitor.dashboard.model.ApmChartConfg;
import com.storm.monitor.dashboard.model.view.ApmChartConfgView;
import com.storm.monitor.dashboard.service.ApmChartConfgService;
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
@RequestMapping("/dashboard_apmchartconfg")
public class ApmChartConfgController {

    @Autowired
    private ApmChartConfgService apmChartConfgService;

    /**
     * 跳转到【图表配置】信息列表页面
     *
     * @param request
     * @return
     */
    @RequestMapping("gotoapmchartconfg")
    public String gotoApmChartConfg(HttpServletRequest request) {
        return "dashboard/apmchartconfg_list";
    }

    /**
     * 查询列表数据
     *
     * @param apmChartConfgView
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping({"/queryapmchartconfg"})
    @ResponseBody
    public List<ApmChartConfg> queryApmChartConfg(ApmChartConfgView apmChartConfgView, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println(JSON.toJSONString(apmChartConfgView));
        List<ApmChartConfg> list = new ArrayList();
        if (apmChartConfgView != null) {
            list = this.apmChartConfgService.queryApmChartConfgByPage(apmChartConfgView);
        }

        return list;
    }

    /**
     * 查询列表数据
     *
     * @param apmChartConfgView
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping({"/queryapmchartconfgpage"})
    @ResponseBody
    public Map queryApmChartConfgPage(ApmChartConfgView apmChartConfgView, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List<ApmChartConfg> list = queryApmChartConfg(apmChartConfgView, request, response);
        int resultCount = (apmChartConfgView == null) ? 0 : this.apmChartConfgService.queryApmChartConfgByCount(apmChartConfgView);

        Map result = new HashMap();
        result.put("rows", list);
        result.put("total", resultCount);
        return result;
    }

    /**
     * 打开【图表配置】信息编辑（新增）页面
     *
     * @param apmChartConfg
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/gotoapmchartconfgedit")
    public String gotoApmChartConfgEdit(ApmChartConfg apmChartConfg, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(JSON.toJSONString(apmChartConfg));
        String id = apmChartConfg.getId();
        if (id != null && !"".equals(id)) {
            apmChartConfg = this.apmChartConfgService.queryApmChartConfgById(id);
        }
        request.setAttribute("pj", apmChartConfg);

        return "dashboard/apmchartconfg_add";
    }

    /**
     * 新增或者删除一条记录
     *
     * @param apmChartConfg
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping({"/addapmchartconfg"})
    @ResponseBody
    public ApmChartConfg addApmChartConfg(ApmChartConfg apmChartConfg, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println(JSON.toJSONString(apmChartConfg));
        ApmChartConfg oldCfg = null;

        if (StringUtils.isEmpty(apmChartConfg.getId())) {
            apmChartConfg.setId(UUIDGenerator.generateUUID40());
        } else {
            oldCfg = this.apmChartConfgService.queryApmChartConfgById(apmChartConfg.getId());
        }

        if (oldCfg == null) {   //新增
            this.apmChartConfgService.addApmChartConfg(apmChartConfg);
        } else {              //更新
            this.apmChartConfgService.updateApmChartConfg(apmChartConfg);
        }

        return apmChartConfg;
    }

    /**
     * 删除配置
     *
     * @param apmChartConfg
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping({"/deleteapmchartconfg"})
    @ResponseBody
    public boolean deleteApmChartConfg(ApmChartConfg apmChartConfg, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println(JSON.toJSONString(apmChartConfg));
        String id = apmChartConfg.getId();
        System.out.println(id);
        int count = this.apmChartConfgService.deleteApmChartConfgById(id);

        return count > 0;

    }

}
