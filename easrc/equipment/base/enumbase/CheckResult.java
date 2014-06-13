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
public class CheckResult extends StringEnum
{
    public static final String NULL_VALUE = "0";//alias=
    public static final String OK_VALUE = "10";//alias=合格
    public static final String NO_VALUE = "20";//alias=不合格

    public static final CheckResult NULL = new CheckResult("NULL", NULL_VALUE);
    public static final CheckResult OK = new CheckResult("OK", OK_VALUE);
    public static final CheckResult NO = new CheckResult("NO", NO_VALUE);

    /**
     * construct function
     * @param String checkResult
     */
    private CheckResult(String name, String checkResult)
    {
        super(name, checkResult);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CheckResult getEnum(String checkResult)
    {
        return (CheckResult)getEnum(CheckResult.class, checkResult);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CheckResult.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CheckResult.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CheckResult.class);
    }
}