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
public class sbStatusType extends StringEnum
{
    public static final String INUSE_VALUE = "1";//alias=‘⁄”√
    public static final String NOTUSE_VALUE = "2";//alias=Õ£”√

    public static final sbStatusType inUse = new sbStatusType("inUse", INUSE_VALUE);
    public static final sbStatusType notUse = new sbStatusType("notUse", NOTUSE_VALUE);

    /**
     * construct function
     * @param String sbStatusType
     */
    private sbStatusType(String name, String sbStatusType)
    {
        super(name, sbStatusType);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static sbStatusType getEnum(String sbStatusType)
    {
        return (sbStatusType)getEnum(sbStatusType.class, sbStatusType);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(sbStatusType.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(sbStatusType.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(sbStatusType.class);
    }
}