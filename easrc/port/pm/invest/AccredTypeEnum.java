/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class AccredTypeEnum extends StringEnum
{
    public static final String TRIAL_VALUE = "1";//alias=初审表
    public static final String ACCRED_VALUE = "2";//alias=评审表
    public static final String APPROVE_VALUE = "3";//alias=批准表

    public static final AccredTypeEnum trial = new AccredTypeEnum("trial", TRIAL_VALUE);
    public static final AccredTypeEnum accred = new AccredTypeEnum("accred", ACCRED_VALUE);
    public static final AccredTypeEnum approve = new AccredTypeEnum("approve", APPROVE_VALUE);

    /**
     * construct function
     * @param String accredTypeEnum
     */
    private AccredTypeEnum(String name, String accredTypeEnum)
    {
        super(name, accredTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static AccredTypeEnum getEnum(String accredTypeEnum)
    {
        return (AccredTypeEnum)getEnum(AccredTypeEnum.class, accredTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(AccredTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(AccredTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(AccredTypeEnum.class);
    }
}