package com.storm.monitor.core.client.model.view;

import com.storm.monitor.core.client.model.ApmBusinessCfg;
import com.storm.monitor.core.entity.PageView;
import com.storm.monitor.core.entity.Page;

/**
 * bean ApmBusinessCfgView Title: 【apm_business_cfg】的View类 
 * Copyriht: Copyright (c) 2020
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2018-09-20 15:40
 *
 */
public class ApmBusinessCfgView extends ApmBusinessCfg implements PageView {

    /**
     * 分页导航
     */
    private Page page = new Page();

    /**
     * 创建时间-开始时间
     */
    private java.util.Date createTimeBegin;
    /**
     * 创建时间-结束时间
     */
    private java.util.Date createTimeEnd;
    /**
     * 修改时间-开始时间
     */
    private java.util.Date modifyTimeBegin;
    /**
     * 修改时间-结束时间
     */
    private java.util.Date modifyTimeEnd;

    public java.util.Date getCreateTimeBegin() {
        return createTimeBegin;
    }

    public void setCreateTimeBegin(java.util.Date createTimeBegin) {
        this.createTimeBegin = createTimeBegin;
    }

    public java.util.Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(java.util.Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public java.util.Date getModifyTimeBegin() {
        return modifyTimeBegin;
    }

    public void setModifyTimeBegin(java.util.Date modifyTimeBegin) {
        this.modifyTimeBegin = modifyTimeBegin;
    }

    public java.util.Date getModifyTimeEnd() {
        return modifyTimeEnd;
    }

    public void setModifyTimeEnd(java.util.Date modifyTimeEnd) {
        this.modifyTimeEnd = modifyTimeEnd;
    }

    @Override
    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

}
