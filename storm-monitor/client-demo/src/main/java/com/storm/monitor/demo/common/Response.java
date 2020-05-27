/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.demo.common;

import java.io.Serializable;

/**
 * 通用反馈对象
 *
 * @author lixin
 */
public class Response<B> implements Serializable {

    //成功代码
    public static final String SUCCESS_CODE = "000000";

    private static final long serialVersionUID = 3234376937233935688L;

    protected String resultCode;
    protected String resultCodeMsg;

    protected B businessObject = null;

    public Response() {

    }

    public Response(String resultCode, B businessObject) {
        this.resultCode = resultCode;
        this.businessObject = businessObject;
    }

    public Response(String resultCode, String resultCodeMsg, B businessObject) {
        this(resultCode, businessObject);
        this.resultCodeMsg = resultCodeMsg;
    }

    public boolean isSuccess() {
        return SUCCESS_CODE.equals(resultCode);
    }

    public boolean isFailure() {
        return !isSuccess();
    }

    public static Response success(){
        return new Response(SUCCESS_CODE,null);
    }
    
    public static Response success(String resultCodeMsg){
        return new Response(SUCCESS_CODE,resultCodeMsg);
    }
    
    public static Response failuer(String resultCode, String resultCodeMsg){
        return new Response(resultCode,resultCodeMsg,null);
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultCodeMsg() {
        return resultCodeMsg;
    }

    public void setResultCodeMsg(String resultCodeMsg) {
        this.resultCodeMsg = resultCodeMsg;
    }

    public B getBusinessObject() {
        return businessObject;
    }

    public void setBusinessObject(B businessObject) {
        this.businessObject = businessObject;
    }
    
    

}
