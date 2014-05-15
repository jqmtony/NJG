/**
 * output package name
 */
package com.kingdee.eas.port.pm.invite;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class isQualified extends StringEnum
{
    public static final String QUALIFIED_VALUE = "1";//alias=合格
    public static final String UNQUALIFIED_VALUE = "2";//alias=不合格

    public static final isQualified qualified = new isQualified("qualified", QUALIFIED_VALUE);
    public static final isQualified unqualified = new isQualified("unqualified", UNQUALIFIED_VALUE);

    /**
     * construct function
     * @param String isQualified
     */
    private isQualified(String name, String isQualified)
    {
        super(name, isQualified);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static isQualified getEnum(String isQualified)
    {
        return (isQualified)getEnum(isQualified.class, isQualified);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(isQualified.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(isQualified.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(isQualified.class);
    }
}