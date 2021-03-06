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
public class FdcExtEnum01 extends StringEnum
{
    public static final String N_01_VALUE = "V_01";//alias=����_01
    public static final String N_02_VALUE = "V_02";//alias=����_02

    public static final FdcExtEnum01 N_01 = new FdcExtEnum01("N_01", N_01_VALUE);
    public static final FdcExtEnum01 N_02 = new FdcExtEnum01("N_02", N_02_VALUE);

    /**
     * construct function
     * @param String fdcExtEnum01
     */
    private FdcExtEnum01(String name, String fdcExtEnum01)
    {
        super(name, fdcExtEnum01);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static FdcExtEnum01 getEnum(String fdcExtEnum01)
    {
        return (FdcExtEnum01)getEnum(FdcExtEnum01.class, fdcExtEnum01);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(FdcExtEnum01.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(FdcExtEnum01.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(FdcExtEnum01.class);
    }
}