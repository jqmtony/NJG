/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.scheme;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class SubClassingModeEnum extends StringEnum
{
    public static final String ABSTRACT_VALUE = "abstract";//alias=抽象的
    public static final String FINAL_VALUE = "final";//alias=最终的
    public static final String NORMAL_VALUE = "normal";//alias=正常的

    public static final SubClassingModeEnum ABSTRACT = new SubClassingModeEnum("ABSTRACT", ABSTRACT_VALUE);
    public static final SubClassingModeEnum FINAL = new SubClassingModeEnum("FINAL", FINAL_VALUE);
    public static final SubClassingModeEnum NORMAL = new SubClassingModeEnum("NORMAL", NORMAL_VALUE);

    /**
     * construct function
     * @param String subClassingModeEnum
     */
    private SubClassingModeEnum(String name, String subClassingModeEnum)
    {
        super(name, subClassingModeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SubClassingModeEnum getEnum(String subClassingModeEnum)
    {
        return (SubClassingModeEnum)getEnum(SubClassingModeEnum.class, subClassingModeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SubClassingModeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SubClassingModeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SubClassingModeEnum.class);
    }
}