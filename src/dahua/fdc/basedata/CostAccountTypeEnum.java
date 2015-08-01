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
public class CostAccountTypeEnum extends StringEnum
{
    public static final String MAIN_VALUE = "MAIN";
    public static final String SIX_VALUE = "SIX";

    public static final CostAccountTypeEnum MAIN = new CostAccountTypeEnum("MAIN", MAIN_VALUE);
    public static final CostAccountTypeEnum SIX = new CostAccountTypeEnum("SIX", SIX_VALUE);

    /**
     * construct function
     * @param String costAccountTypeEnum
     */
    private CostAccountTypeEnum(String name, String costAccountTypeEnum)
    {
        super(name, costAccountTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CostAccountTypeEnum getEnum(String costAccountTypeEnum)
    {
        return (CostAccountTypeEnum)getEnum(CostAccountTypeEnum.class, costAccountTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CostAccountTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CostAccountTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CostAccountTypeEnum.class);
    }
}