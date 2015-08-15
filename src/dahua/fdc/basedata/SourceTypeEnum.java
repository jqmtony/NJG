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
public class SourceTypeEnum extends IntEnum
{
    public static final int IMP_VALUE = 0;//alias=系统导入
    public static final int CREATE_VALUE = 1;//alias=关联生成
    public static final int ADDNEW_VALUE = 2;//alias=手工新增

    public static final SourceTypeEnum IMP = new SourceTypeEnum("IMP", IMP_VALUE);
    public static final SourceTypeEnum CREATE = new SourceTypeEnum("CREATE", CREATE_VALUE);
    public static final SourceTypeEnum ADDNEW = new SourceTypeEnum("ADDNEW", ADDNEW_VALUE);

    /**
     * construct function
     * @param integer sourceTypeEnum
     */
    private SourceTypeEnum(String name, int sourceTypeEnum)
    {
        super(name, sourceTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SourceTypeEnum getEnum(String sourceTypeEnum)
    {
        return (SourceTypeEnum)getEnum(SourceTypeEnum.class, sourceTypeEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static SourceTypeEnum getEnum(int sourceTypeEnum)
    {
        return (SourceTypeEnum)getEnum(SourceTypeEnum.class, sourceTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SourceTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SourceTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SourceTypeEnum.class);
    }
}