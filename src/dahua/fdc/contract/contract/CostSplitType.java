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
public class CostSplitType extends StringEnum
{
    public static final String PROJSPLIT_VALUE = "PROJSPLIT";
    public static final String BOTUPSPLIT_VALUE = "BOTUPSPLIT";
    public static final String PRODSPLIT_VALUE = "PRODSPLIT";
    public static final String MANUALSPLIT_VALUE = "MANUALSPLIT";

    public static final CostSplitType PROJSPLIT = new CostSplitType("PROJSPLIT", PROJSPLIT_VALUE);
    public static final CostSplitType BOTUPSPLIT = new CostSplitType("BOTUPSPLIT", BOTUPSPLIT_VALUE);
    public static final CostSplitType PRODSPLIT = new CostSplitType("PRODSPLIT", PRODSPLIT_VALUE);
    public static final CostSplitType MANUALSPLIT = new CostSplitType("MANUALSPLIT", MANUALSPLIT_VALUE);

    /**
     * construct function
     * @param String costSplitType
     */
    private CostSplitType(String name, String costSplitType)
    {
        super(name, costSplitType);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CostSplitType getEnum(String costSplitType)
    {
        return (CostSplitType)getEnum(CostSplitType.class, costSplitType);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CostSplitType.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CostSplitType.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CostSplitType.class);
    }
}