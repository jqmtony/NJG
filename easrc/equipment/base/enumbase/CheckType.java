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
public class CheckType extends StringEnum
{
    public static final String CITY_VALUE = "10";//alias=ÊÐ¼ì
    public static final String PORT_VALUE = "20";//alias=¸Û¼ì
    public static final String CHUAN_VALUE = "30";//alias=´¬¼ì

    public static final CheckType city = new CheckType("city", CITY_VALUE);
    public static final CheckType port = new CheckType("port", PORT_VALUE);
    public static final CheckType chuan = new CheckType("chuan", CHUAN_VALUE);

    /**
     * construct function
     * @param String checkType
     */
    private CheckType(String name, String checkType)
    {
        super(name, checkType);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CheckType getEnum(String checkType)
    {
        return (CheckType)getEnum(CheckType.class, checkType);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CheckType.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CheckType.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CheckType.class);
    }
}