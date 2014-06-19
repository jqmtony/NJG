/**
 * output package name
 */
package com.kingdee.eas.fdc.contract;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class ResponsibleStyleEnum extends StringEnum
{
    public static final String ALLCONTAIN_VALUE = "AllContain";//alias=总价及综合单价都包干
    public static final String WITHOUTTOTAL_VALUE = "WithOutTotal";//alias=综合单价包干总价暂定

    public static final ResponsibleStyleEnum AllContain = new ResponsibleStyleEnum("AllContain", ALLCONTAIN_VALUE);
    public static final ResponsibleStyleEnum WithOutTotal = new ResponsibleStyleEnum("WithOutTotal", WITHOUTTOTAL_VALUE);

    /**
     * construct function
     * @param String responsibleStyleEnum
     */
    private ResponsibleStyleEnum(String name, String responsibleStyleEnum)
    {
        super(name, responsibleStyleEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ResponsibleStyleEnum getEnum(String responsibleStyleEnum)
    {
        return (ResponsibleStyleEnum)getEnum(ResponsibleStyleEnum.class, responsibleStyleEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ResponsibleStyleEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ResponsibleStyleEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ResponsibleStyleEnum.class);
    }
}