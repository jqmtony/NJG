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
public class DepartmentEnum extends StringEnum
{
    public static final String GCB_VALUE = "GCB";//alias=工程部
    public static final String PTB_VALUE = "PTB";//alias=配套部
    public static final String CBB_VALUE = "CBB";//alias=成本部
    public static final String SJB_VALUE = "SJB";//alias=设计部

    public static final DepartmentEnum GCB = new DepartmentEnum("GCB", GCB_VALUE);
    public static final DepartmentEnum PTB = new DepartmentEnum("PTB", PTB_VALUE);
    public static final DepartmentEnum CBB = new DepartmentEnum("CBB", CBB_VALUE);
    public static final DepartmentEnum SJB = new DepartmentEnum("SJB", SJB_VALUE);

    /**
     * construct function
     * @param String departmentEnum
     */
    private DepartmentEnum(String name, String departmentEnum)
    {
        super(name, departmentEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static DepartmentEnum getEnum(String departmentEnum)
    {
        return (DepartmentEnum)getEnum(DepartmentEnum.class, departmentEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(DepartmentEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(DepartmentEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(DepartmentEnum.class);
    }
}