/**
 * output package name
 */
package com.kingdee.eas.port.equipment.base.enumbase;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class CostType extends StringEnum
{
    public static final String POWERDRIVEN_VALUE = "1";//alias=电动机械
    public static final String ICDRIVEN_VALUE = "2";//alias=内燃机械

    public static final CostType powerDriven = new CostType("powerDriven", POWERDRIVEN_VALUE);
    public static final CostType ICDriven = new CostType("ICDriven", ICDRIVEN_VALUE);

    /**
     * construct function
     * @param String costType
     */
    private CostType(String name, String costType)
    {
        super(name, costType);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CostType getEnum(String costType)
    {
        return (CostType)getEnum(CostType.class, costType);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CostType.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CostType.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CostType.class);
    }
}