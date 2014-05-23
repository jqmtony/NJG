/**
 * output package name
 */
package com.kingdee.eas.port.pm.base.coms;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class AdjustType extends StringEnum
{
    public static final String ESTIMATES_VALUE = "10";//alias=估算调整
    public static final String PROBABLY_VALUE = "20";//alias=概算调整
    public static final String BUDGET_VALUE = "30";//alias=预算调整

    public static final AdjustType Estimates = new AdjustType("Estimates", ESTIMATES_VALUE);
    public static final AdjustType Probably = new AdjustType("Probably", PROBABLY_VALUE);
    public static final AdjustType Budget = new AdjustType("Budget", BUDGET_VALUE);

    /**
     * construct function
     * @param String adjustType
     */
    private AdjustType(String name, String adjustType)
    {
        super(name, adjustType);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static AdjustType getEnum(String adjustType)
    {
        return (AdjustType)getEnum(AdjustType.class, adjustType);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(AdjustType.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(AdjustType.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(AdjustType.class);
    }
}