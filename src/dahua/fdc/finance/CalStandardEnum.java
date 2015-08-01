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
public class CalStandardEnum extends IntEnum
{
    public static final int CONTRACTAMOUNT_VALUE = 1;
    public static final int SCHEDULEAMOUNT_VALUE = 2;

    public static final CalStandardEnum CONTRACTAMOUNT = new CalStandardEnum("CONTRACTAMOUNT", CONTRACTAMOUNT_VALUE);
    public static final CalStandardEnum SCHEDULEAMOUNT = new CalStandardEnum("SCHEDULEAMOUNT", SCHEDULEAMOUNT_VALUE);

    /**
     * construct function
     * @param integer calStandardEnum
     */
    private CalStandardEnum(String name, int calStandardEnum)
    {
        super(name, calStandardEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CalStandardEnum getEnum(String calStandardEnum)
    {
        return (CalStandardEnum)getEnum(CalStandardEnum.class, calStandardEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static CalStandardEnum getEnum(int calStandardEnum)
    {
        return (CalStandardEnum)getEnum(CalStandardEnum.class, calStandardEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CalStandardEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CalStandardEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CalStandardEnum.class);
    }
}