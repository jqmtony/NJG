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
public class ChangeUrgentDegreeEnum extends StringEnum
{
    public static final String NORMAL_VALUE = "1normal";
    public static final String URGENT_VALUE = "2urgent";

    public static final ChangeUrgentDegreeEnum Normal = new ChangeUrgentDegreeEnum("Normal", NORMAL_VALUE);
    public static final ChangeUrgentDegreeEnum Urgent = new ChangeUrgentDegreeEnum("Urgent", URGENT_VALUE);

    /**
     * construct function
     * @param String changeUrgentDegreeEnum
     */
    private ChangeUrgentDegreeEnum(String name, String changeUrgentDegreeEnum)
    {
        super(name, changeUrgentDegreeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ChangeUrgentDegreeEnum getEnum(String changeUrgentDegreeEnum)
    {
        return (ChangeUrgentDegreeEnum)getEnum(ChangeUrgentDegreeEnum.class, changeUrgentDegreeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ChangeUrgentDegreeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ChangeUrgentDegreeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ChangeUrgentDegreeEnum.class);
    }
}