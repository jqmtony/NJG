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
public class DeductModeEnum extends StringEnum
{
    public static final String JSKK_VALUE = "1";

    public static final DeductModeEnum JSKK = new DeductModeEnum("JSKK", JSKK_VALUE);

    /**
     * construct function
     * @param String deductModeEnum
     */
    private DeductModeEnum(String name, String deductModeEnum)
    {
        super(name, deductModeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static DeductModeEnum getEnum(String deductModeEnum)
    {
        return (DeductModeEnum)getEnum(DeductModeEnum.class, deductModeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(DeductModeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(DeductModeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(DeductModeEnum.class);
    }
}