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
public class DanFangTypeEnum extends StringEnum
{
    public static final String BUILD_VALUE = "BUILD";
    public static final String SALE_VALUE = "SALE";

    public static final DanFangTypeEnum BUILD = new DanFangTypeEnum("BUILD", BUILD_VALUE);
    public static final DanFangTypeEnum SALE = new DanFangTypeEnum("SALE", SALE_VALUE);

    /**
     * construct function
     * @param String danFangTypeEnum
     */
    private DanFangTypeEnum(String name, String danFangTypeEnum)
    {
        super(name, danFangTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static DanFangTypeEnum getEnum(String danFangTypeEnum)
    {
        return (DanFangTypeEnum)getEnum(DanFangTypeEnum.class, danFangTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(DanFangTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(DanFangTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(DanFangTypeEnum.class);
    }
}