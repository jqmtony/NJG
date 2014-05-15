/**
 * output package name
 */
package com.kingdee.eas.port.equipment.base.enumbase;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class nowStatusType extends StringEnum
{
    public static final String WELL_VALUE = "1";//alias=Á¼ºÃ
    public static final String GENERAL_VALUE = "2";//alias=Ò»°ã
    public static final String WORSE_VALUE = "3";//alias=½Ï²î

    public static final nowStatusType well = new nowStatusType("well", WELL_VALUE);
    public static final nowStatusType general = new nowStatusType("general", GENERAL_VALUE);
    public static final nowStatusType Worse = new nowStatusType("Worse", WORSE_VALUE);

    /**
     * construct function
     * @param String nowStatusType
     */
    private nowStatusType(String name, String nowStatusType)
    {
        super(name, nowStatusType);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static nowStatusType getEnum(String nowStatusType)
    {
        return (nowStatusType)getEnum(nowStatusType.class, nowStatusType);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(nowStatusType.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(nowStatusType.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(nowStatusType.class);
    }
}