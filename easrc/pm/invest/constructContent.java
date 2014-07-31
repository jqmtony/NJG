/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class constructContent extends StringEnum
{
    public static final String BASECONSTRUCT_VALUE = "1";//alias=基本建设
    public static final String NONBASECONSTRUCT_VALUE = "2";//alias=非基本建设

    public static final constructContent baseConstruct = new constructContent("baseConstruct", BASECONSTRUCT_VALUE);
    public static final constructContent nonBaseConstruct = new constructContent("nonBaseConstruct", NONBASECONSTRUCT_VALUE);

    /**
     * construct function
     * @param String constructContent
     */
    private constructContent(String name, String constructContent)
    {
        super(name, constructContent);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static constructContent getEnum(String constructContent)
    {
        return (constructContent)getEnum(constructContent.class, constructContent);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(constructContent.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(constructContent.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(constructContent.class);
    }
}