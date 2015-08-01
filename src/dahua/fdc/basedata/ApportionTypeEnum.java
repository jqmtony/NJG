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
public class ApportionTypeEnum extends StringEnum
{
    public static final String STANDARD_VALUE = "0STANDARD";
    public static final String CUSTOM_VALUE = "1CUSTOM";

    public static final ApportionTypeEnum STANDARD = new ApportionTypeEnum("STANDARD", STANDARD_VALUE);
    public static final ApportionTypeEnum CUSTOM = new ApportionTypeEnum("CUSTOM", CUSTOM_VALUE);

    /**
     * construct function
     * @param String apportionTypeEnum
     */
    private ApportionTypeEnum(String name, String apportionTypeEnum)
    {
        super(name, apportionTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ApportionTypeEnum getEnum(String apportionTypeEnum)
    {
        return (ApportionTypeEnum)getEnum(ApportionTypeEnum.class, apportionTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ApportionTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ApportionTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ApportionTypeEnum.class);
    }
}