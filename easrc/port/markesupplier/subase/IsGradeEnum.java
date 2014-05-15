/**
 * output package name
 */
package com.kingdee.eas.port.markesupplier.subase;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class IsGradeEnum extends StringEnum
{
    public static final String ELIGIBILITY_VALUE = "1";//alias=合格
    public static final String ENGRADE_VALUE = "2";//alias=不合格

    public static final IsGradeEnum ELIGIBILITY = new IsGradeEnum("ELIGIBILITY", ELIGIBILITY_VALUE);
    public static final IsGradeEnum ENGRADE = new IsGradeEnum("ENGRADE", ENGRADE_VALUE);

    /**
     * construct function
     * @param String isGradeEnum
     */
    private IsGradeEnum(String name, String isGradeEnum)
    {
        super(name, isGradeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static IsGradeEnum getEnum(String isGradeEnum)
    {
        return (IsGradeEnum)getEnum(IsGradeEnum.class, isGradeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(IsGradeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(IsGradeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(IsGradeEnum.class);
    }
}