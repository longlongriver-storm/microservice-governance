package com.storm.monitor.core.client.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * bean ApmBusinessCfg Title:
 * Copyriht: Copyright (c) 2020
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2018-09-20 15:40
 *
 */
public class ApmBusinessCfg implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private java.lang.Integer id;
    /**
     * 业务主键
     */
    private java.lang.String businessKey;
    /**
     * 标题
     */
    private java.lang.String title;
    /**
     * 描述
     */
    private java.lang.String description;
    /**
     * 类名方法名
     */
    private java.lang.String classMethodName;
    /**
     * 参数JSON
     */
    private java.lang.String parameters;
    /**
     * 返回参数
     */
    private java.lang.String returnResult;
    /**
     * 创建时间
     */
    private java.util.Date createTime;
    /**
     * 修改时间
     */
    private java.util.Date modifyTime;

    public java.lang.Integer getId() {
        return id;
    }

    public void setId(java.lang.Integer id) {
        this.id = id;
    }

    public java.lang.String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(java.lang.String businessKey) {
        this.businessKey = businessKey;
    }

    public java.lang.String getTitle() {
        return title;
    }

    public void setTitle(java.lang.String title) {
        this.title = title;
    }

    public java.lang.String getDescription() {
        return description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public java.lang.String getClassMethodName() {
        return classMethodName;
    }

    public void setClassMethodName(java.lang.String classMethodName) {
        this.classMethodName = classMethodName;
    }

    public java.lang.String getParameters() {
        return parameters;
    }

    public void setParameters(java.lang.String parameters) {
        this.parameters = parameters;
    }

    public java.lang.String getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(java.lang.String returnResult) {
        this.returnResult = returnResult;
    }

    public java.util.Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    public java.util.Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(java.util.Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 复制所有属性到指定对象
     *
     * @param targetApmBusinessCfg 属性赋值目标对象
     */
    public void copyPropertiesTo(ApmBusinessCfg targetApmBusinessCfg) {
        if (targetApmBusinessCfg == null) {
            return;
        }
        targetApmBusinessCfg.setId(this.getId());
        targetApmBusinessCfg.setBusinessKey(this.getBusinessKey());
        targetApmBusinessCfg.setTitle(this.getTitle());
        targetApmBusinessCfg.setDescription(this.getDescription());
        targetApmBusinessCfg.setClassMethodName(this.getClassMethodName());
        targetApmBusinessCfg.setParameters(this.getParameters());
        targetApmBusinessCfg.setReturnResult(this.getReturnResult());
        targetApmBusinessCfg.setCreateTime(this.getCreateTime());
        targetApmBusinessCfg.setModifyTime(this.getModifyTime());
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ApmBusinessCfg)) {
            return false;
        }
        ApmBusinessCfg apmBusinessCfg2 = (ApmBusinessCfg) object;
        return new EqualsBuilder()
            .appendSuper(super.equals(object))
            .append(this.id, apmBusinessCfg2.id)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(-528253723, -475504089)
            .appendSuper(super.hashCode())
            .append(this.id)
            .toHashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append("id=").append(this.getId()).append(",");
        sb.append("businessKey=").append(this.getBusinessKey()).append(",");
        sb.append("title=").append(this.getTitle()).append(",");
        sb.append("description=").append(this.getDescription()).append(",");
        sb.append("classMethodName=").append(this.getClassMethodName()).append(",");
        sb.append("parameters=").append(this.getParameters()).append(",");
        sb.append("returnResult=").append(this.getReturnResult()).append(",");
        sb.append("createTime=").append(this.getCreateTime()).append(",");
        sb.append("modifyTime=").append(this.getModifyTime());
        sb.append("]");
        return sb.toString();
    }

}
