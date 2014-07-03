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
    public static final String NULL_VALUE = "0";//alias=
    public static final String WELL_VALUE = "1";//alias=良好
    public static final String GENERAL_VALUE = "2";//alias=一般
    public static final String WORSE_VALUE = "3";//alias=较差
    public static final String RENT_VALUE = "4";//alias=租用
    public static final String ASSETSTRAN_VALUE = "5";//alias=资产转移

    public static final nowStatusType NULL = new nowStatusType("NULL", NULL_VALUE);
    public static final nowStatusType well = new nowStatusType("well", WELL_VALUE);
    public static final nowStatusType general = new nowStatusType("general", GENERAL_VALUE);
    public static final nowStatusType Worse = new nowStatusType("Worse", WORSE_VALUE);
    public static final nowStatusType rent = new nowStatusType("rent", RENT_VALUE);
    public static final nowStatusType assetstran = new nowStatusType("assetstran", ASSETSTRAN_VALUE);

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