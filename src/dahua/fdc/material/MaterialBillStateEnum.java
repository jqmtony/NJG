/**
 * output package name
 */
package com.kingdee.eas.fdc.material;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class MaterialBillStateEnum extends StringEnum
{
    public static final String PART_VALUE = "1";
    public static final String ALL_VALUE = "2";
    public static final String NONE_VALUE = "3";
    public static final String NONE_ARRIVED_VALUE = "4";
    public static final String PART_ARRIVED_VALUE = "5";
    public static final String ALL_ARRIVED_VALUE = "6";

    public static final MaterialBillStateEnum PART = new MaterialBillStateEnum("PART", PART_VALUE);
    public static final MaterialBillStateEnum ALL = new MaterialBillStateEnum("ALL", ALL_VALUE);
    public static final MaterialBillStateEnum NONE = new MaterialBillStateEnum("NONE", NONE_VALUE);
    public static final MaterialBillStateEnum NONE_ARRIVED = new MaterialBillStateEnum("NONE_ARRIVED", NONE_ARRIVED_VALUE);
    public static final MaterialBillStateEnum PART_ARRIVED = new MaterialBillStateEnum("PART_ARRIVED", PART_ARRIVED_VALUE);
    public static final MaterialBillStateEnum ALL_ARRIVED = new MaterialBillStateEnum("ALL_ARRIVED", ALL_ARRIVED_VALUE);

    /**
     * construct function
     * @param String materialBillStateEnum
     */
    private MaterialBillStateEnum(String name, String materialBillStateEnum)
    {
        super(name, materialBillStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static MaterialBillStateEnum getEnum(String materialBillStateEnum)
    {
        return (MaterialBillStateEnum)getEnum(MaterialBillStateEnum.class, materialBillStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(MaterialBillStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(MaterialBillStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(MaterialBillStateEnum.class);
    }
}