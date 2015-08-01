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
public class FdcStatisticsTypeEnum extends StringEnum
{
    public static final String NONE_VALUE = "001_NONE";//alias=
    public static final String SUM_VALUE = "002_SUM";//alias=求和
    public static final String COUNT_VALUE = "003_COUNT";//alias=计数
    public static final String MAX_VALUE = "004_MAX";//alias=最大值
    public static final String MIN_VALUE = "005_MIN";//alias=最小值
    public static final String AVG_VALUE = "006_AVG";//alias=平均值

    public static final FdcStatisticsTypeEnum NONE = new FdcStatisticsTypeEnum("NONE", NONE_VALUE);
    public static final FdcStatisticsTypeEnum SUM = new FdcStatisticsTypeEnum("SUM", SUM_VALUE);
    public static final FdcStatisticsTypeEnum COUNT = new FdcStatisticsTypeEnum("COUNT", COUNT_VALUE);
    public static final FdcStatisticsTypeEnum MAX = new FdcStatisticsTypeEnum("MAX", MAX_VALUE);
    public static final FdcStatisticsTypeEnum MIN = new FdcStatisticsTypeEnum("MIN", MIN_VALUE);
    public static final FdcStatisticsTypeEnum AVG = new FdcStatisticsTypeEnum("AVG", AVG_VALUE);

    /**
     * construct function
     * @param String fdcStatisticsTypeEnum
     */
    private FdcStatisticsTypeEnum(String name, String fdcStatisticsTypeEnum)
    {
        super(name, fdcStatisticsTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static FdcStatisticsTypeEnum getEnum(String fdcStatisticsTypeEnum)
    {
        return (FdcStatisticsTypeEnum)getEnum(FdcStatisticsTypeEnum.class, fdcStatisticsTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(FdcStatisticsTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(FdcStatisticsTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(FdcStatisticsTypeEnum.class);
    }
}