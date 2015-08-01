/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class StandardIndexEnum extends StringEnum
{
    public static final String MAN_VALUE = "0MAN";
    public static final String BUILDAREA_VALUE = "1BUILDAREA";
    public static final String SELLAREA_VALUE = "2SELLAREA";
    public static final String CONTAINAREA_VALUE = "3CONTAINAREA";
    public static final String CUBAGERATE_VALUE = "4CUBAGERATE";
    public static final String PRODUCTRATE_VALUE = "5PRODUCTRATE";
    public static final String UNITAREA_VALUE = "6UNITAREA";
    public static final String UNITS_VALUE = "7UNITS";
    public static final String DOORS_VALUE = "8DOORS";

    public static final StandardIndexEnum MAN = new StandardIndexEnum("MAN", MAN_VALUE);
    public static final StandardIndexEnum BUILDAREA = new StandardIndexEnum("BUILDAREA", BUILDAREA_VALUE);
    public static final StandardIndexEnum SELLAREA = new StandardIndexEnum("SELLAREA", SELLAREA_VALUE);
    public static final StandardIndexEnum CONTAINAREA = new StandardIndexEnum("CONTAINAREA", CONTAINAREA_VALUE);
    public static final StandardIndexEnum CUBAGERATE = new StandardIndexEnum("CUBAGERATE", CUBAGERATE_VALUE);
    public static final StandardIndexEnum PRODUCTRATE = new StandardIndexEnum("PRODUCTRATE", PRODUCTRATE_VALUE);
    public static final StandardIndexEnum UNITAREA = new StandardIndexEnum("UNITAREA", UNITAREA_VALUE);
    public static final StandardIndexEnum UNITS = new StandardIndexEnum("UNITS", UNITS_VALUE);
    public static final StandardIndexEnum DOORS = new StandardIndexEnum("DOORS", DOORS_VALUE);

    /**
     * construct function
     * @param String standardIndexEnum
     */
    private StandardIndexEnum(String name, String standardIndexEnum)
    {
        super(name, standardIndexEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static StandardIndexEnum getEnum(String standardIndexEnum)
    {
        return (StandardIndexEnum)getEnum(StandardIndexEnum.class, standardIndexEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(StandardIndexEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(StandardIndexEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(StandardIndexEnum.class);
    }
}