/**
 * output package name
 */
package com.kingdee.eas.custom.richbase;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class BizStateEnum extends StringEnum
{
    public static final String WKP_VALUE = "30";//alias=未开票
    public static final String YKP_VALUE = "10";//alias=已开票
    public static final String YSK_VALUE = "20";//alias=已收款

    public static final BizStateEnum wkp = new BizStateEnum("wkp", WKP_VALUE);
    public static final BizStateEnum ykp = new BizStateEnum("ykp", YKP_VALUE);
    public static final BizStateEnum ysk = new BizStateEnum("ysk", YSK_VALUE);

    /**
     * construct function
     * @param String bizStateEnum
     */
    private BizStateEnum(String name, String bizStateEnum)
    {
        super(name, bizStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BizStateEnum getEnum(String bizStateEnum)
    {
        return (BizStateEnum)getEnum(BizStateEnum.class, bizStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BizStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BizStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BizStateEnum.class);
    }
}