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
public class CostSplitTypeEnum extends StringEnum
{
    public static final String PROJSPLIT_VALUE = "PROJSPLIT";
    public static final String BOTUPSPLIT_VALUE = "BOTUPSPLIT";
    public static final String PRODSPLIT_VALUE = "PRODSPLIT";
    public static final String MANUALSPLIT_VALUE = "MANUALSPLIT";

    public static final CostSplitTypeEnum PROJSPLIT = new CostSplitTypeEnum("PROJSPLIT", PROJSPLIT_VALUE);
    public static final CostSplitTypeEnum BOTUPSPLIT = new CostSplitTypeEnum("BOTUPSPLIT", BOTUPSPLIT_VALUE);
    public static final CostSplitTypeEnum PRODSPLIT = new CostSplitTypeEnum("PRODSPLIT", PRODSPLIT_VALUE);
    public static final CostSplitTypeEnum MANUALSPLIT = new CostSplitTypeEnum("MANUALSPLIT", MANUALSPLIT_VALUE);

    /**
     * construct function
     * @param String costSplitTypeEnum
     */
    private CostSplitTypeEnum(String name, String costSplitTypeEnum)
    {
        super(name, costSplitTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CostSplitTypeEnum getEnum(String costSplitTypeEnum)
    {
        return (CostSplitTypeEnum)getEnum(CostSplitTypeEnum.class, costSplitTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CostSplitTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CostSplitTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CostSplitTypeEnum.class);
    }
}