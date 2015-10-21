/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class DataTypeEnum extends IntEnum
{
    public static final int DATE_VALUE = 10;//alias=日期类型
    public static final int BOOL_VALUE = 20;//alias=布尔类型
    public static final int NUMBER_VALUE = 30;//alias=数值类型
    public static final int STRING_VALUE = 40;//alias=字符类型
    public static final int BASEDATA_VALUE = 50;//alias=基础资料

    public static final DataTypeEnum DATE = new DataTypeEnum("DATE", DATE_VALUE);
    public static final DataTypeEnum BOOL = new DataTypeEnum("BOOL", BOOL_VALUE);
    public static final DataTypeEnum NUMBER = new DataTypeEnum("NUMBER", NUMBER_VALUE);
    public static final DataTypeEnum STRING = new DataTypeEnum("STRING", STRING_VALUE);
    public static final DataTypeEnum BASEDATA = new DataTypeEnum("BASEDATA", BASEDATA_VALUE);

    /**
     * construct function
     * @param integer dataTypeEnum
     */
    private DataTypeEnum(String name, int dataTypeEnum)
    {
        super(name, dataTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static DataTypeEnum getEnum(String dataTypeEnum)
    {
        return (DataTypeEnum)getEnum(DataTypeEnum.class, dataTypeEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static DataTypeEnum getEnum(int dataTypeEnum)
    {
        return (DataTypeEnum)getEnum(DataTypeEnum.class, dataTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(DataTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(DataTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(DataTypeEnum.class);
    }
}