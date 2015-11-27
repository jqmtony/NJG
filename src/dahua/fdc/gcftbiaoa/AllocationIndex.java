/**
 * output package name
 */
package com.kingdee.eas.fdc.gcftbiaoa;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class AllocationIndex extends StringEnum
{
    public static final String COVEREDAREA_VALUE = "1";//alias=建筑面积
    public static final String SALEAREA_VALUE = "2";//alias=销售面积

    public static final AllocationIndex coveredArea = new AllocationIndex("coveredArea", COVEREDAREA_VALUE);
    public static final AllocationIndex saleArea = new AllocationIndex("saleArea", SALEAREA_VALUE);

    /**
     * construct function
     * @param String allocationIndex
     */
    private AllocationIndex(String name, String allocationIndex)
    {
        super(name, allocationIndex);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static AllocationIndex getEnum(String allocationIndex)
    {
        return (AllocationIndex)getEnum(AllocationIndex.class, allocationIndex);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(AllocationIndex.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(AllocationIndex.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(AllocationIndex.class);
    }
}