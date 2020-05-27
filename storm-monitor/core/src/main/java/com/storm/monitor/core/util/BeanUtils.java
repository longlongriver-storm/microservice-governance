/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.core.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 *
 * @author lixin
 */
public class BeanUtils {

    private static HashMap<String, Object> baseTypes = new HashMap<String, Object>();
    private static String[] datePattern = new String[]{"yyyy-MM", "yyyyMM", "yyyy/MM",
        "yyyyMMdd", "yyyy-MM-dd", "yyyy/MM/dd",
        "yyyyMMddHHmmss",
        "yyyy-MM-dd HH:mm:ss",
        "yyyy/MM/dd HH:mm:ss"};

    static {
        baseTypes.put(String.class.getCanonicalName(), "000");
        baseTypes.put(Integer.class.getCanonicalName(), new Integer(0));
        baseTypes.put(Long.class.getCanonicalName(), new Long(0));
        baseTypes.put(Float.class.getCanonicalName(), new Float(0));
        baseTypes.put(Double.class.getCanonicalName(), new Double(0));
        baseTypes.put(Short.class.getCanonicalName(), Short.valueOf("0"));
        baseTypes.put(BigInteger.class.getCanonicalName(), new BigDecimal("0"));
        baseTypes.put(BigDecimal.class.getCanonicalName(), new BigDecimal("0"));

        baseTypes.put(Character.class.getCanonicalName(), Character.valueOf('a'));
        baseTypes.put(Date.class.getCanonicalName(), new Date());
        baseTypes.put(java.sql.Date.class.getCanonicalName(), new java.sql.Date((new Date()).getTime()));
        baseTypes.put(java.sql.Timestamp.class.getCanonicalName(), new java.sql.Timestamp((new Date()).getTime()));

        baseTypes.put(Boolean.class.getCanonicalName(), Boolean.TRUE);
        baseTypes.put("int", new Integer(0));
        baseTypes.put("long", new Long(0));
        baseTypes.put("float", new Float(0));
        baseTypes.put("double", new Double(0));
        baseTypes.put("short", Short.valueOf("0"));
        baseTypes.put("boolean", Boolean.TRUE);
        baseTypes.put(java.util.List.class.getCanonicalName(), new ArrayList());
        baseTypes.put(java.util.Collection.class.getCanonicalName(), new ArrayList());
        baseTypes.put(java.util.Map.class.getCanonicalName(), new HashMap());
    }

    /**
     * 是否是基本类型
     *
     * @param obj
     * @return true 是基本类型，false 不是基本类型或者无法判断
     */
    public static boolean isBaseType(Object obj) {
        if (obj == null) {
            return false;
        }
        if (baseTypes.containsKey(obj.getClass().getCanonicalName())) {
            return true;
        }
        return false;
    }

    public static boolean isEquals(Object obj, String val) throws Exception {
        //org.apache.commons.beanutils.PropertyUtils.setProperty(obj, val, obj);
        if (obj == null) {
            if (val == null) {
                return true;
            } else {
                return false;
            }
        }

        if (obj instanceof String) {
            return val.equals(obj.toString());
        }
        if (obj instanceof Integer) {
            return Integer.valueOf(val).equals(obj);
        }
        if (obj instanceof Long) {
            return Long.valueOf(val).equals(obj);
        }

        if (obj instanceof Float) {
            return Float.valueOf(val).equals(obj);
        }

        if (obj instanceof Double) {
            return Double.valueOf(val).equals(obj);
        }

        if (obj instanceof Short) {
            return Short.valueOf(val).equals(obj);
        }
        if (obj instanceof BigInteger) {
            return (new BigInteger(val)).equals(obj);
        }

        if (obj instanceof BigDecimal) {
            return (new BigDecimal(val)).equals(obj);
        }
        if (obj instanceof Character) {
            return val.equals(obj.toString());
        }
        if (obj instanceof Date) {
            Date d1 = DateUtils.parseDate(val, datePattern);
            return d1.equals(obj);
        }

        if (obj instanceof java.sql.Date) {
            Date d1 = DateUtils.parseDate(val, datePattern);
            return d1.getTime() == ((java.sql.Date) obj).getTime();
        }
        if (obj instanceof java.sql.Timestamp) {
            Date d1 = DateUtils.parseDate(val, datePattern);
            return d1.getTime() == ((java.sql.Timestamp) obj).getTime();
        }
        if (obj instanceof Boolean) {
            return Boolean.parseBoolean(val)==((Boolean)obj);
        }

        return false;
    }
    
    /**
     * 用String来构建具体的对象
     * @param obj
     * @param val
     * @return
     * @throws Exception 
     */
    public static Object convertString2Object(Object obj, String val) throws Exception {
        return convertString2Object(obj.getClass(),val);
    }
    
    public static Object convertString2Object(Class clazz, String val) throws Exception {
        //org.apache.commons.beanutils.PropertyUtils.setProperty(obj, val, obj);
        if (val == null) {
            return null;
        }

        if (clazz==String.class) {
            return val;
        }
        if (clazz==Integer.class) {
            return Integer.valueOf(val);
        }
        if (clazz==Long.class) {
            return Long.valueOf(val);
        }

        if (clazz==Float.class) {
            return Float.valueOf(val);
        }

        if (clazz==Double.class) {
            return Double.valueOf(val);
        }

        if (clazz==Short.class) {
            return Short.valueOf(val);
        }
        if (clazz==BigInteger.class) {
            return (new BigInteger(val));
        }

        if (clazz==BigDecimal.class) {
            return (new BigDecimal(val));
        }
        if (clazz==Character.class) {
            return val.charAt(0);
        }
        if (clazz==Date.class) {
            Date d1 = DateUtils.parseDate(val, datePattern);
            return d1;
        }

        if (clazz==java.sql.Date.class) {
            Date d1 = DateUtils.parseDate(val, datePattern);
            return new java.sql.Date(d1.getTime());
        }
        if (clazz==java.sql.Timestamp.class) {
            Date d1 = DateUtils.parseDate(val, datePattern);
            return new java.sql.Timestamp(d1.getTime());
        }
        if (clazz==Boolean.class) {
            return Boolean.parseBoolean(val);
        }

        return null;
    }
    
    /**
     * 获取对象中的某个属性值
     * @param obj
     * @param paramName
     * @return
     * @throws Exception 当取不到值时，抛出错误（注意，取不到值和取到null值是两回事）
     */
    public static Object getPropertyValue(Object obj, String paramName) throws Exception {
        if (null==paramName || "".equals(paramName)) {
            return obj;
        }
        String[] param = paramName.split("\\.");
        Object childObj = obj;
        for (int i = 0; i < param.length; i++) {
            if (childObj == null) {  //为空了
                throw new Exception("Object is null,can not get property:" + param[i]);
            }
            childObj = PropertyUtils.getProperty(childObj, param[i]);

        }
        //到这里的话，已经顺利获得属性值了
        return childObj;
    }

}
