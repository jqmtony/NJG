/**
 * output package name
 */
package com.kingdee.eas.fdc.finance;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class PayPlanModeEnum extends IntEnum
{
    public static final int BYSCHEDULE_VALUE = 1;
    public static final int BYMONTH_VALUE = 2;

    public static final PayPlanModeEnum BYSCHEDULE = new PayPlanModeEnum("BYSCHEDULE", BYSCHEDULE_VALUE);
    public static final PayPlanModeEnum BYMONTH = new PayPlanModeEnum("BYMONTH", BYMONTH_VALUE);

    /**
     * construct function
     * @param integer payPlanModeEnum
     */
    private PayPlanModeEnum(String name, int payPlanModeEnum)
    {
        super(name, payPlanModeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PayPlanModeEnum getEnum(String payPlanModeEnum)
    {
        return (PayPlanModeEnum)getEnum(PayPlanModeEnum.class, payPlanModeEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static PayPlanModeEnum getEnum(int payPlanModeEnum)
    {
        return (PayPlanModeEnum)getEnum(PayPlanModeEnum.class, payPlanModeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PayPlanModeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PayPlanModeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PayPlanModeEnum.class);
    }
}