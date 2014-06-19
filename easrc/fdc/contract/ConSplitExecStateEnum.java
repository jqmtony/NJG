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
public class ConSplitExecStateEnum extends StringEnum
{
    public static final String COMMON_VALUE = "COMMON";//alias=正常
    public static final String INVALID_VALUE = "INVALID";//alias=作废待处理
    public static final String INVALIDDID_VALUE = "INVALIDDID";//alias=作废已处理

    public static final ConSplitExecStateEnum COMMON = new ConSplitExecStateEnum("COMMON", COMMON_VALUE);
    public static final ConSplitExecStateEnum INVALID = new ConSplitExecStateEnum("INVALID", INVALID_VALUE);
    public static final ConSplitExecStateEnum INVALIDDID = new ConSplitExecStateEnum("INVALIDDID", INVALIDDID_VALUE);

    /**
     * construct function
     * @param String conSplitExecStateEnum
     */
    private ConSplitExecStateEnum(String name, String conSplitExecStateEnum)
    {
        super(name, conSplitExecStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ConSplitExecStateEnum getEnum(String conSplitExecStateEnum)
    {
        return (ConSplitExecStateEnum)getEnum(ConSplitExecStateEnum.class, conSplitExecStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ConSplitExecStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ConSplitExecStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ConSplitExecStateEnum.class);
    }
}