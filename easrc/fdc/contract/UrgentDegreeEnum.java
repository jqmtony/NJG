/**
 * output package name
 */
package com.kingdee.eas.fdc.contract;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class UrgentDegreeEnum extends IntEnum
{
    public static final int NORMAL_VALUE = 1;//alias=∆’Õ®
    public static final int URGENT_VALUE = 2;//alias=º”º±

    public static final UrgentDegreeEnum NORMAL = new UrgentDegreeEnum("NORMAL", NORMAL_VALUE);
    public static final UrgentDegreeEnum URGENT = new UrgentDegreeEnum("URGENT", URGENT_VALUE);

    /**
     * construct function
     * @param integer urgentDegreeEnum
     */
    private UrgentDegreeEnum(String name, int urgentDegreeEnum)
    {
        super(name, urgentDegreeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static UrgentDegreeEnum getEnum(String urgentDegreeEnum)
    {
        return (UrgentDegreeEnum)getEnum(UrgentDegreeEnum.class, urgentDegreeEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static UrgentDegreeEnum getEnum(int urgentDegreeEnum)
    {
        return (UrgentDegreeEnum)getEnum(UrgentDegreeEnum.class, urgentDegreeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(UrgentDegreeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(UrgentDegreeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(UrgentDegreeEnum.class);
    }
}