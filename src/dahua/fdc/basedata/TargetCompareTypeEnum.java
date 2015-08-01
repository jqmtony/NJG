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
public class TargetCompareTypeEnum extends StringEnum
{
    public static final String EQUAL_VALUE = "0";//alias=等于
    public static final String LIKE_VALUE = "1";//alias=类似于
    public static final String GREATER_VALUE = "2";//alias=大于
    public static final String SMALLER_VALUE = "3";//alias=小于
    public static final String NOTEQUAL_VALUE = "4";//alias=不等于

    public static final TargetCompareTypeEnum Equal = new TargetCompareTypeEnum("Equal", EQUAL_VALUE);
    public static final TargetCompareTypeEnum Like = new TargetCompareTypeEnum("Like", LIKE_VALUE);
    public static final TargetCompareTypeEnum Greater = new TargetCompareTypeEnum("Greater", GREATER_VALUE);
    public static final TargetCompareTypeEnum Smaller = new TargetCompareTypeEnum("Smaller", SMALLER_VALUE);
    public static final TargetCompareTypeEnum NotEqual = new TargetCompareTypeEnum("NotEqual", NOTEQUAL_VALUE);

    /**
     * construct function
     * @param String targetCompareTypeEnum
     */
    private TargetCompareTypeEnum(String name, String targetCompareTypeEnum)
    {
        super(name, targetCompareTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TargetCompareTypeEnum getEnum(String targetCompareTypeEnum)
    {
        return (TargetCompareTypeEnum)getEnum(TargetCompareTypeEnum.class, targetCompareTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TargetCompareTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TargetCompareTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TargetCompareTypeEnum.class);
    }
}