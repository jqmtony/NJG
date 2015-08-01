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
public class ChinaNumberEnum extends StringEnum
{
    public static final String R0_VALUE = "0";
    public static final String R1_VALUE = "1";
    public static final String R2_VALUE = "2";
    public static final String R3_VALUE = "3";
    public static final String R4_VALUE = "4";
    public static final String R5_VALUE = "5";
    public static final String R6_VALUE = "6";
    public static final String R7_VALUE = "7";
    public static final String R8_VALUE = "8";
    public static final String R9_VALUE = "9";

    public static final ChinaNumberEnum R0 = new ChinaNumberEnum("R0", R0_VALUE);
    public static final ChinaNumberEnum R1 = new ChinaNumberEnum("R1", R1_VALUE);
    public static final ChinaNumberEnum R2 = new ChinaNumberEnum("R2", R2_VALUE);
    public static final ChinaNumberEnum R3 = new ChinaNumberEnum("R3", R3_VALUE);
    public static final ChinaNumberEnum R4 = new ChinaNumberEnum("R4", R4_VALUE);
    public static final ChinaNumberEnum R5 = new ChinaNumberEnum("R5", R5_VALUE);
    public static final ChinaNumberEnum R6 = new ChinaNumberEnum("R6", R6_VALUE);
    public static final ChinaNumberEnum R7 = new ChinaNumberEnum("R7", R7_VALUE);
    public static final ChinaNumberEnum R8 = new ChinaNumberEnum("R8", R8_VALUE);
    public static final ChinaNumberEnum R9 = new ChinaNumberEnum("R9", R9_VALUE);

    /**
     * construct function
     * @param String chinaNumberEnum
     */
    private ChinaNumberEnum(String name, String chinaNumberEnum)
    {
        super(name, chinaNumberEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ChinaNumberEnum getEnum(String chinaNumberEnum)
    {
        return (ChinaNumberEnum)getEnum(ChinaNumberEnum.class, chinaNumberEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ChinaNumberEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ChinaNumberEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ChinaNumberEnum.class);
    }
}