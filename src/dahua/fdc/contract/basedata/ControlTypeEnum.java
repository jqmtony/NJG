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
public class ControlTypeEnum extends StringEnum
{
    public static final String ONE_VALUE = "one";
    public static final String TWO_VALUE = "two";
    public static final String THREE_VALUE = "three";

    public static final ControlTypeEnum one = new ControlTypeEnum("one", ONE_VALUE);
    public static final ControlTypeEnum two = new ControlTypeEnum("two", TWO_VALUE);
    public static final ControlTypeEnum three = new ControlTypeEnum("three", THREE_VALUE);

    /**
     * construct function
     * @param String controlTypeEnum
     */
    private ControlTypeEnum(String name, String controlTypeEnum)
    {
        super(name, controlTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ControlTypeEnum getEnum(String controlTypeEnum)
    {
        return (ControlTypeEnum)getEnum(ControlTypeEnum.class, controlTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ControlTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ControlTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ControlTypeEnum.class);
    }
}