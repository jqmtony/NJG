/**
 * output package name
 */
package com.kingdee.eas.fdc.finance;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class CalTypeEnum extends IntEnum
{
    public static final int TIME_VALUE = 1;
    public static final int TIMERANGE_VALUE = 2;

    public static final CalTypeEnum TIME = new CalTypeEnum("TIME", TIME_VALUE);
    public static final CalTypeEnum TIMERANGE = new CalTypeEnum("TIMERANGE", TIMERANGE_VALUE);

    /**
     * construct function
     * @param integer calTypeEnum
     */
    private CalTypeEnum(String name, int calTypeEnum)
    {
        super(name, calTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CalTypeEnum getEnum(String calTypeEnum)
    {
        return (CalTypeEnum)getEnum(CalTypeEnum.class, calTypeEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static CalTypeEnum getEnum(int calTypeEnum)
    {
        return (CalTypeEnum)getEnum(CalTypeEnum.class, calTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CalTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CalTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CalTypeEnum.class);
    }
}