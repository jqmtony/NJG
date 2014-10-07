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
    public static final String CITY_VALUE = "10";//alias=市检
    public static final String PORT_VALUE = "20";//alias=港检
    public static final String CHUAN_VALUE = "30";//alias=船检
    public static final String CAR_VALUE = "40";//alias=车管所
    public static final String YIBIAO_VALUE = "50";//alias=仪表

    public static final CheckType city = new CheckType("city", CITY_VALUE);
    public static final CheckType port = new CheckType("port", PORT_VALUE);
    public static final CheckType chuan = new CheckType("chuan", CHUAN_VALUE);
    public static final CheckType car = new CheckType("car", CAR_VALUE);
    public static final CheckType yibiao = new CheckType("yibiao", YIBIAO_VALUE);

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