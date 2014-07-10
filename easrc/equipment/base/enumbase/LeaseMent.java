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
public class LeaseMent extends StringEnum
{
    public static final String INRENT_VALUE = "0";//alias=×âÈë
    public static final String OUTRENT_VALUE = "1";//alias=×â³ö

    public static final LeaseMent INRENT = new LeaseMent("INRENT", INRENT_VALUE);
    public static final LeaseMent OUTRENT = new LeaseMent("OUTRENT", OUTRENT_VALUE);

    /**
     * construct function
     * @param String leaseMent
     */
    private LeaseMent(String name, String leaseMent)
    {
        super(name, leaseMent);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static LeaseMent getEnum(String leaseMent)
    {
        return (LeaseMent)getEnum(LeaseMent.class, leaseMent);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(LeaseMent.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(LeaseMent.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(LeaseMent.class);
    }
}