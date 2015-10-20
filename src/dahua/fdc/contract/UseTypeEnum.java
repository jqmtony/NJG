/**
 * output package name
 */
package com.kingdee.eas.fdc.contract;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class UseTypeEnum extends StringEnum
{
    public static final String JDK_VALUE = "JDK";//alias=进度款
    public static final String BCHTK_VALUE = "BCHTK";//alias=补充合同付款
    public static final String JSK_VALUE = "JSK";//alias=结算款
    public static final String WK_VALUE = "WK";//alias=尾款
    public static final String PHNZ_VALUE = "PHNZ";//alias=配合拿证
    public static final String GGF_VALUE = "GGF";//alias=赶工费
    public static final String PHJF_VALUE = "PHJF";//alias=配合交房
    public static final String PHKP_VALUE = "PHKP";//alias=配合开盘
    public static final String QT_VALUE = "QT";//alias=其他

    public static final UseTypeEnum JDK = new UseTypeEnum("JDK", JDK_VALUE);
    public static final UseTypeEnum BCHTK = new UseTypeEnum("BCHTK", BCHTK_VALUE);
    public static final UseTypeEnum JSK = new UseTypeEnum("JSK", JSK_VALUE);
    public static final UseTypeEnum WK = new UseTypeEnum("WK", WK_VALUE);
    public static final UseTypeEnum PHNZ = new UseTypeEnum("PHNZ", PHNZ_VALUE);
    public static final UseTypeEnum GGF = new UseTypeEnum("GGF", GGF_VALUE);
    public static final UseTypeEnum PHJF = new UseTypeEnum("PHJF", PHJF_VALUE);
    public static final UseTypeEnum PHKP = new UseTypeEnum("PHKP", PHKP_VALUE);
    public static final UseTypeEnum QT = new UseTypeEnum("QT", QT_VALUE);

    /**
     * construct function
     * @param String useTypeEnum
     */
    private UseTypeEnum(String name, String useTypeEnum)
    {
        super(name, useTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static UseTypeEnum getEnum(String useTypeEnum)
    {
        return (UseTypeEnum)getEnum(UseTypeEnum.class, useTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(UseTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(UseTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(UseTypeEnum.class);
    }
}