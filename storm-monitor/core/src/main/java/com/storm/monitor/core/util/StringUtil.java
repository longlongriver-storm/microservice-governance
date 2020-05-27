/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.core.util;

/**
 *
 * @author lixin
 */
public class StringUtil {
    /**
     * 获取两个字符串之间的字符串
     * @param str
     * @param leftStr
     * @param rightStr
     * @return 
     */
    public static String getSubStr(String str, String leftStr, String rightStr) {
        if(str==null || leftStr==null || rightStr==null){
            return "";
        }
        int idx1 = str.indexOf(leftStr);
        int idx2 = str.indexOf(rightStr);
        if (idx1 <= idx2) {
            return str.substring(idx1+leftStr.length(), idx2);
        }
        return "";

    }
    
}
