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
public class determineThenWay extends StringEnum
{
    public static final String EXTRACTING_VALUE = "extracting";//alias=≥È»°
    public static final String SPECIFIED_VALUE = "specified";//alias=÷∏∂®

    public static final determineThenWay extracting = new determineThenWay("extracting", EXTRACTING_VALUE);
    public static final determineThenWay specified = new determineThenWay("specified", SPECIFIED_VALUE);

    /**
     * construct function
     * @param String determineThenWay
     */
    private determineThenWay(String name, String determineThenWay)
    {
        super(name, determineThenWay);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static determineThenWay getEnum(String determineThenWay)
    {
        return (determineThenWay)getEnum(determineThenWay.class, determineThenWay);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(determineThenWay.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(determineThenWay.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(determineThenWay.class);
    }
}