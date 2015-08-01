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
public class NumberUnitEnum extends StringEnum
{
    public static final String R1_VALUE = "1";
    public static final String R2_VALUE = "2";
    public static final String R3_VALUE = "3";
    public static final String R4_VALUE = "4";
    public static final String R5_VALUE = "5";

    public static final NumberUnitEnum R1 = new NumberUnitEnum("R1", R1_VALUE);
    public static final NumberUnitEnum R2 = new NumberUnitEnum("R2", R2_VALUE);
    public static final NumberUnitEnum R3 = new NumberUnitEnum("R3", R3_VALUE);
    public static final NumberUnitEnum R4 = new NumberUnitEnum("R4", R4_VALUE);
    public static final NumberUnitEnum R5 = new NumberUnitEnum("R5", R5_VALUE);

    /**
     * construct function
     * @param String numberUnitEnum
     */
    private NumberUnitEnum(String name, String numberUnitEnum)
    {
        super(name, numberUnitEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static NumberUnitEnum getEnum(String numberUnitEnum)
    {
        return (NumberUnitEnum)getEnum(NumberUnitEnum.class, numberUnitEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(NumberUnitEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(NumberUnitEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(NumberUnitEnum.class);
    }
}