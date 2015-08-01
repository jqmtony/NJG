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
public class BusinessTypeEnum extends StringEnum
{
    public static final String ONE_VALUE = "one";
    public static final String TWO_VALUE = "two";
    public static final String THREE_VALUE = "three";

    public static final BusinessTypeEnum one = new BusinessTypeEnum("one", ONE_VALUE);
    public static final BusinessTypeEnum two = new BusinessTypeEnum("two", TWO_VALUE);
    public static final BusinessTypeEnum three = new BusinessTypeEnum("three", THREE_VALUE);

    /**
     * construct function
     * @param String businessTypeEnum
     */
    private BusinessTypeEnum(String name, String businessTypeEnum)
    {
        super(name, businessTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BusinessTypeEnum getEnum(String businessTypeEnum)
    {
        return (BusinessTypeEnum)getEnum(BusinessTypeEnum.class, businessTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BusinessTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BusinessTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BusinessTypeEnum.class);
    }
}