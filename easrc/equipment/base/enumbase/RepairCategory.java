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
public class RepairCategory extends StringEnum
{
    public static final String SELFSTUDY_VALUE = "1";//alias=自修
    public static final String OUTREPAIR_VALUE = "2";//alias=委外修理

    public static final RepairCategory selfstudy = new RepairCategory("selfstudy", SELFSTUDY_VALUE);
    public static final RepairCategory outrepair = new RepairCategory("outrepair", OUTREPAIR_VALUE);

    /**
     * construct function
     * @param String repairCategory
     */
    private RepairCategory(String name, String repairCategory)
    {
        super(name, repairCategory);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RepairCategory getEnum(String repairCategory)
    {
        return (RepairCategory)getEnum(RepairCategory.class, repairCategory);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RepairCategory.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RepairCategory.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RepairCategory.class);
    }
}