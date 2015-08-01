/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class TargetStatusEnum extends StringEnum
{
    public static final String Y_VALUE = "1";//alias=ÊÇ
    public static final String N_VALUE = "0";//alias=·ñ

    public static final TargetStatusEnum Y = new TargetStatusEnum("Y", Y_VALUE);
    public static final TargetStatusEnum N = new TargetStatusEnum("N", N_VALUE);

    /**
     * construct function
     * @param String targetStatusEnum
     */
    private TargetStatusEnum(String name, String targetStatusEnum)
    {
        super(name, targetStatusEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TargetStatusEnum getEnum(String targetStatusEnum)
    {
        return (TargetStatusEnum)getEnum(TargetStatusEnum.class, targetStatusEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TargetStatusEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TargetStatusEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TargetStatusEnum.class);
    }
}