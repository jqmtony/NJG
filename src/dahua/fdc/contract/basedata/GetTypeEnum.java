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
public class GetTypeEnum extends StringEnum
{
    public static final String ONE_VALUE = "one";
    public static final String TWO_VALUE = "two";
    public static final String THREE_VALUE = "three";

    public static final GetTypeEnum one = new GetTypeEnum("one", ONE_VALUE);
    public static final GetTypeEnum two = new GetTypeEnum("two", TWO_VALUE);
    public static final GetTypeEnum three = new GetTypeEnum("three", THREE_VALUE);

    /**
     * construct function
     * @param String getTypeEnum
     */
    private GetTypeEnum(String name, String getTypeEnum)
    {
        super(name, getTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static GetTypeEnum getEnum(String getTypeEnum)
    {
        return (GetTypeEnum)getEnum(GetTypeEnum.class, getTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(GetTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(GetTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(GetTypeEnum.class);
    }
}