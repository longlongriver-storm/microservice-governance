package com.storm.monitor.dashboard.controller;

import com.alibaba.fastjson.JSON;
import com.storm.monitor.core.client.model.ApmBusinessCfg;
import com.storm.monitor.core.client.model.view.ApmBusinessCfgView;
import com.storm.monitor.core.client.service.ApmBusinessCfgService;
import java.util.ArrayList;
import java.util.Date;
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
@RequestMapping("/dashboard_apmbusinesscfg")
public class ApmBusinessCfgController {

    @Autowired
    private ApmBusinessCfgService apmBusinessCfgService;

    /**
     * 跳转到【apm_business_cfg】信息列表页面
     *
     * @param request
     * @return
     */
    @RequestMapping("gotoapmbusinesscfg")
    public String gotoApmBusinessCfg(HttpServletRequest request) {
        return "dashboard/apmbusinesscfg_list";
    }

    /**
     * 查询列表数据
     *
     * @param apmBusinessCfgView
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping({"/queryapmbusinesscfg"})
    @ResponseBody
    public List<ApmBusinessCfg> queryApmBusinessCfg(ApmBusinessCfgView apmBusinessCfgView, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println(JSON.toJSONString(apmBusinessCfgView));
        List<ApmBusinessCfg> list = new ArrayList();
        if (apmBusinessCfgView != null) {
            list = this.apmBusinessCfgService.queryApmBusinessCfgByPage(apmBusinessCfgView);
        }

        return list;
    }

    /**
     * 查询列表数据
     *
     * @param apmBusinessCfgView
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping({"/queryapmbusinesscfgpage"})
    @ResponseBody
    public Map queryApmBusinessCfgPage(ApmBusinessCfgView apmBusinessCfgView, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List<ApmBusinessCfg> list = queryApmBusinessCfg(apmBusinessCfgView, request, response);
        int resultCount = (apmBusinessCfgView == null) ? 0 : this.apmBusinessCfgService.queryApmBusinessCfgByCount(apmBusinessCfgView);

        Map result = new HashMap();
        result.put("rows", list);
        result.put("total", resultCount);
        return result;
    }

    /**
     * 打开【apm_business_cfg】信息编辑（新增）页面
     *
     * @param apmBusinessCfg
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/gotoapmbusinesscfgedit")
    public String gotoApmBusinessCfgEdit(ApmBusinessCfg apmBusinessCfg, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(JSON.toJSONString(apmBusinessCfg));
        Integer id = apmBusinessCfg.getId();
        if (id != null && !"".equals(id)) {
            apmBusinessCfg = this.apmBusinessCfgService.queryApmBusinessCfgById(id);

        }
        request.setAttribute("pj", apmBusinessCfg);

        return "dashboard/apmbusinesscfg_add";
    }

    /**
     * 新增或者删除一条记录
     *
     * @param apmBusinessCfg
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping({"/addapmbusinesscfg"})
    @ResponseBody
    public ApmBusinessCfg addApmBusinessCfg(ApmBusinessCfg apmBusinessCfg, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println(JSON.toJSONString(apmBusinessCfg));
        ApmBusinessCfg oldCfg = null;

        if (null != apmBusinessCfg.getId()) {
            oldCfg = this.apmBusinessCfgService.queryApmBusinessCfgById(apmBusinessCfg.getId());
        }

        if (oldCfg == null) {   //新增
            apmBusinessCfg.setCreateTime(new Date());
            apmBusinessCfg.setModifyTime(new Date());
            this.apmBusinessCfgService.addApmBusinessCfg(apmBusinessCfg);
        } else {              //更新
            apmBusinessCfg.setModifyTime(new Date());
            this.apmBusinessCfgService.updateApmBusinessCfg(apmBusinessCfg);
        }

        return apmBusinessCfg;
    }

    /**
     * 删除配置
     *
     * @param apmBusinessCfg
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping({"/deleteapmbusinesscfg"})
    @ResponseBody
    public boolean deleteApmBusinessCfg(ApmBusinessCfg apmBusinessCfg, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println(JSON.toJSONString(apmBusinessCfg));
        Integer id = apmBusinessCfg.getId();
        System.out.println(id);
        int count = this.apmBusinessCfgService.deleteApmBusinessCfgById(id);

        return count > 0;

    }

}
