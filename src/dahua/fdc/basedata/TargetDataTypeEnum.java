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
public class TargetDataTypeEnum extends StringEnum
{
    public static final String DATETYPE_VALUE = "1";//alias=日期型字段
    public static final String STRINGTYPE_VALUE = "2";//alias=字符
    public static final String PERCENTAGE_VALUE = "3";//alias=数字型（百分数）
    public static final String NUMBER_VALUE = "4";//alias=数字

    public static final TargetDataTypeEnum DateType = new TargetDataTypeEnum("DateType", DATETYPE_VALUE);
    public static final TargetDataTypeEnum StringType = new TargetDataTypeEnum("StringType", STRINGTYPE_VALUE);
    public static final TargetDataTypeEnum Percentage = new TargetDataTypeEnum("Percentage", PERCENTAGE_VALUE);
    public static final TargetDataTypeEnum Number = new TargetDataTypeEnum("Number", NUMBER_VALUE);

    /**
     * construct function
     * @param String targetDataTypeEnum
     */
    private TargetDataTypeEnum(String name, String targetDataTypeEnum)
    {
        super(name, targetDataTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TargetDataTypeEnum getEnum(String targetDataTypeEnum)
    {
        return (TargetDataTypeEnum)getEnum(TargetDataTypeEnum.class, targetDataTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TargetDataTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TargetDataTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TargetDataTypeEnum.class);
    }
}