/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.settle.app;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class TrialStatusEnum extends IntEnum
{
    public static final int REVIEW_VALUE = 1;//alias=¥˝…Û
    public static final int INTRIAL_VALUE = 2;//alias=‘⁄…Û
    public static final int APPROVED_VALUE = 3;//alias=…Û∂®

    public static final TrialStatusEnum Review = new TrialStatusEnum("Review", REVIEW_VALUE);
    public static final TrialStatusEnum InTrial = new TrialStatusEnum("InTrial", INTRIAL_VALUE);
    public static final TrialStatusEnum Approved = new TrialStatusEnum("Approved", APPROVED_VALUE);

    /**
     * construct function
     * @param integer trialStatusEnum
     */
    private TrialStatusEnum(String name, int trialStatusEnum)
    {
        super(name, trialStatusEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TrialStatusEnum getEnum(String trialStatusEnum)
    {
        return (TrialStatusEnum)getEnum(TrialStatusEnum.class, trialStatusEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static TrialStatusEnum getEnum(int trialStatusEnum)
    {
        return (TrialStatusEnum)getEnum(TrialStatusEnum.class, trialStatusEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TrialStatusEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TrialStatusEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TrialStatusEnum.class);
    }
}