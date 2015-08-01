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
public class MathOperateEnum extends StringEnum
{
    public static final String ADD_VALUE = "01";//alias=加
    public static final String SUBTRACT_VALUE = "02";//alias=减
    public static final String MULTIPLY_VALUE = "03";//alias=乘
    public static final String DIVIDE_VALUE = "04";//alias=除
    public static final String NEGATE_VALUE = "05";//alias=相反值
    public static final String ABS_VALUE = "06";//alias=绝对值

    public static final MathOperateEnum ADD = new MathOperateEnum("ADD", ADD_VALUE);
    public static final MathOperateEnum SUBTRACT = new MathOperateEnum("SUBTRACT", SUBTRACT_VALUE);
    public static final MathOperateEnum MULTIPLY = new MathOperateEnum("MULTIPLY", MULTIPLY_VALUE);
    public static final MathOperateEnum DIVIDE = new MathOperateEnum("DIVIDE", DIVIDE_VALUE);
    public static final MathOperateEnum NEGATE = new MathOperateEnum("NEGATE", NEGATE_VALUE);
    public static final MathOperateEnum ABS = new MathOperateEnum("ABS", ABS_VALUE);

    /**
     * construct function
     * @param String mathOperateEnum
     */
    private MathOperateEnum(String name, String mathOperateEnum)
    {
        super(name, mathOperateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static MathOperateEnum getEnum(String mathOperateEnum)
    {
        return (MathOperateEnum)getEnum(MathOperateEnum.class, mathOperateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(MathOperateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(MathOperateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(MathOperateEnum.class);
    }
}