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
public class TargetDataStatisticsTypeEnum extends StringEnum
{
    public static final String NONE_VALUE = "1NONE";//alias=不统计
    public static final String SUM_VALUE = "2SUM";//alias=求和
    public static final String AVERAGE_VALUE = "3AVERAGE";//alias=平均值

    public static final TargetDataStatisticsTypeEnum NONE = new TargetDataStatisticsTypeEnum("NONE", NONE_VALUE);
    public static final TargetDataStatisticsTypeEnum SUM = new TargetDataStatisticsTypeEnum("SUM", SUM_VALUE);
    public static final TargetDataStatisticsTypeEnum AVERAGE = new TargetDataStatisticsTypeEnum("AVERAGE", AVERAGE_VALUE);

    /**
     * construct function
     * @param String targetDataStatisticsTypeEnum
     */
    private TargetDataStatisticsTypeEnum(String name, String targetDataStatisticsTypeEnum)
    {
        super(name, targetDataStatisticsTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TargetDataStatisticsTypeEnum getEnum(String targetDataStatisticsTypeEnum)
    {
        return (TargetDataStatisticsTypeEnum)getEnum(TargetDataStatisticsTypeEnum.class, targetDataStatisticsTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TargetDataStatisticsTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TargetDataStatisticsTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TargetDataStatisticsTypeEnum.class);
    }
}