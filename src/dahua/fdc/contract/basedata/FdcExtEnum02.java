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
public class FdcExtEnum02 extends StringEnum
{
    public static final String N_01_VALUE = "V_01";//alias=别名_01
    public static final String N_02_VALUE = "V_02";//alias=别名_02

    public static final FdcExtEnum02 N_01 = new FdcExtEnum02("N_01", N_01_VALUE);
    public static final FdcExtEnum02 N_02 = new FdcExtEnum02("N_02", N_02_VALUE);

    /**
     * construct function
     * @param String fdcExtEnum02
     */
    private FdcExtEnum02(String name, String fdcExtEnum02)
    {
        super(name, fdcExtEnum02);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static FdcExtEnum02 getEnum(String fdcExtEnum02)
    {
        return (FdcExtEnum02)getEnum(FdcExtEnum02.class, fdcExtEnum02);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(FdcExtEnum02.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(FdcExtEnum02.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(FdcExtEnum02.class);
    }
}