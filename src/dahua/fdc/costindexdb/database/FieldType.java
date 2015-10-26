/**
 * output package name
 */
package com.kingdee.eas.fdc.costindexdb.database;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class FieldType extends StringEnum
{
    public static final String TEXT_VALUE = "10";//alias=文本
    public static final String NUMBER_VALUE = "20";//alias=数字
    public static final String DATE_VALUE = "30";//alias=日期
    public static final String PRODUCTYPE_VALUE = "40";//alias=产品类型
    public static final String BASEUNIT_VALUE = "50";//alias=计量单位
    public static final String COMPUTE_VALUE = "60";//alias=公式计算
    public static final String BUILDNUM_VALUE = "70";//alias=典型楼号

    public static final FieldType TEXT = new FieldType("TEXT", TEXT_VALUE);
    public static final FieldType NUMBER = new FieldType("NUMBER", NUMBER_VALUE);
    public static final FieldType DATE = new FieldType("DATE", DATE_VALUE);
    public static final FieldType PRODUCTYPE = new FieldType("PRODUCTYPE", PRODUCTYPE_VALUE);
    public static final FieldType BASEUNIT = new FieldType("BASEUNIT", BASEUNIT_VALUE);
    public static final FieldType COMPUTE = new FieldType("COMPUTE", COMPUTE_VALUE);
    public static final FieldType BUILDNUM = new FieldType("BUILDNUM", BUILDNUM_VALUE);

    /**
     * construct function
     * @param String fieldType
     */
    private FieldType(String name, String fieldType)
    {
        super(name, fieldType);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static FieldType getEnum(String fieldType)
    {
        return (FieldType)getEnum(FieldType.class, fieldType);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(FieldType.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(FieldType.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(FieldType.class);
    }
}