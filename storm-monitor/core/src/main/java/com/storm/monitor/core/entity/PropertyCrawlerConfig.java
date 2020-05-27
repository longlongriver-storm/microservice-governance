/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.core.entity;

/**
 * 单个属性抓取定义器
 * @author lixin
 */
public class PropertyCrawlerConfig {
    /**
     * 入参类型
     */
    public static final String PROPERTY_TYPE_INPUT="input";
    /**
     * 出参类型
     */
    public static final String PROPERTY_TYPE_OUTPUT="output";
    /**
     * 如果抓取出参的时候用这个名称，表示抓取整个出参
     */
    public static final String PROPERTY_NAME_OUTPUT="[all]";
    /**
     * 属性类型
     */
    String propertyType;
    /**
     * 入参位置(此参数对出参无意义)，0表示第一个入参，以此类推
     */
    int inputIndex;
    /**
     * 属性名称，如果是多重的话，可以用“.”隔开，如"fundInfo.fundCode"
     */
    String propertyName;

    /**
     * 创建入参抓取定义
     * @param inputIndex
     * @param propertyName 
     */
    public PropertyCrawlerConfig(int inputIndex, String propertyName) {
        this.propertyType=PROPERTY_TYPE_INPUT;
        this.inputIndex = inputIndex;
        this.propertyName = propertyName;
    }

    /**
     * 创建出参抓取定义
     * @param propertyName 
     */
    public PropertyCrawlerConfig(String propertyName) {
        this.propertyType=PROPERTY_TYPE_OUTPUT;
        this.propertyName = propertyName;
    }


    public String getPropertyType() {
        return propertyType;
    }

    public int getInputIndex() {
        return inputIndex;
    }

    public void setInputIndex(int inputIndex) {
        this.inputIndex = inputIndex;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
    
    public String getPropertyKey(){
        if(PROPERTY_TYPE_INPUT.equals(this.propertyType)){   //入参抓取
            if(null==this.getPropertyName() || "".equals(this.getPropertyName())){
                return this.getPropertyType()+"."+this.getInputIndex();
            }else{
                return this.getPropertyType()+"."+this.getInputIndex()+"."+this.getPropertyName();
            }
            
        }else{ //出参
            if(PROPERTY_NAME_OUTPUT.equals(this.getPropertyName())){   //整个出参
                return this.getPropertyType();
            }else{   //出参中的属性
                return this.getPropertyType()+"."+this.getPropertyName();
            }
        }
    }
}
