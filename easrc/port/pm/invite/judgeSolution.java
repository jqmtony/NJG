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
public class judgeSolution extends StringEnum
{
    public static final String INTEGRATE_VALUE = "1";//alias=综合评分法
    public static final String LOWEST_VALUE = "2";//alias=最低评标价法

    public static final judgeSolution integrate = new judgeSolution("integrate", INTEGRATE_VALUE);
    public static final judgeSolution lowest = new judgeSolution("lowest", LOWEST_VALUE);

    /**
     * construct function
     * @param String judgeSolution
     */
    private judgeSolution(String name, String judgeSolution)
    {
        super(name, judgeSolution);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static judgeSolution getEnum(String judgeSolution)
    {
        return (judgeSolution)getEnum(judgeSolution.class, judgeSolution);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(judgeSolution.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(judgeSolution.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(judgeSolution.class);
    }
}