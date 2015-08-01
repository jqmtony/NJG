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
public class VirtualProductTypeEnum extends StringEnum
{
    public static final String WHOLE_PROJ_VALUE = "WHOLE_PROJ";

    public static final VirtualProductTypeEnum WHOLE_PROJ = new VirtualProductTypeEnum("WHOLE_PROJ", WHOLE_PROJ_VALUE);

    /**
     * construct function
     * @param String virtualProductTypeEnum
     */
    private VirtualProductTypeEnum(String name, String virtualProductTypeEnum)
    {
        super(name, virtualProductTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static VirtualProductTypeEnum getEnum(String virtualProductTypeEnum)
    {
        return (VirtualProductTypeEnum)getEnum(VirtualProductTypeEnum.class, virtualProductTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(VirtualProductTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(VirtualProductTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(VirtualProductTypeEnum.class);
    }
}