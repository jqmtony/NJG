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
public class CostSplitStateEnum extends StringEnum
{
    public static final String NOSPLIT_VALUE = "1NOSPLIT";//alias=未拆分
    public static final String PARTSPLIT_VALUE = "2PARTSPLIT";//alias=部分拆分
    public static final String ALLSPLIT_VALUE = "3ALLSPLIT";//alias=全部拆分

    public static final CostSplitStateEnum NOSPLIT = new CostSplitStateEnum("NOSPLIT", NOSPLIT_VALUE);
    public static final CostSplitStateEnum PARTSPLIT = new CostSplitStateEnum("PARTSPLIT", PARTSPLIT_VALUE);
    public static final CostSplitStateEnum ALLSPLIT = new CostSplitStateEnum("ALLSPLIT", ALLSPLIT_VALUE);

    /**
     * construct function
     * @param String costSplitStateEnum
     */
    private CostSplitStateEnum(String name, String costSplitStateEnum)
    {
        super(name, costSplitStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CostSplitStateEnum getEnum(String costSplitStateEnum)
    {
        return (CostSplitStateEnum)getEnum(CostSplitStateEnum.class, costSplitStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CostSplitStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CostSplitStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CostSplitStateEnum.class);
    }
}