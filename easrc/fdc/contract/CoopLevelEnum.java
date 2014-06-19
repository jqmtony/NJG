/**
 * output package name
 */
package com.kingdee.eas.fdc.contract;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class CoopLevelEnum extends StringEnum
{
    public static final String A_VALUE = "A";//alias=A级-集团范围内战略合作
    public static final String B_VALUE = "B";//alias=B级-区域范围内战略合作
    public static final String C_VALUE = "C";//alias=C级-城市范围内战略合作
    public static final String D_VALUE = "D";//alias=D级-公司范围内战略合作
    public static final String E_VALUE = "E";//alias=E级-项目范围内战略合作

    public static final CoopLevelEnum A = new CoopLevelEnum("A", A_VALUE);
    public static final CoopLevelEnum B = new CoopLevelEnum("B", B_VALUE);
    public static final CoopLevelEnum C = new CoopLevelEnum("C", C_VALUE);
    public static final CoopLevelEnum D = new CoopLevelEnum("D", D_VALUE);
    public static final CoopLevelEnum E = new CoopLevelEnum("E", E_VALUE);

    /**
     * construct function
     * @param String coopLevelEnum
     */
    private CoopLevelEnum(String name, String coopLevelEnum)
    {
        super(name, coopLevelEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CoopLevelEnum getEnum(String coopLevelEnum)
    {
        return (CoopLevelEnum)getEnum(CoopLevelEnum.class, coopLevelEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CoopLevelEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CoopLevelEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CoopLevelEnum.class);
    }
}