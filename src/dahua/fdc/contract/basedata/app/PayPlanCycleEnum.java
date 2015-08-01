/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.app;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class PayPlanCycleEnum extends IntEnum
{
    public static final int ONE_VALUE = 1;
    public static final int TWO_VALUE = 2;
    public static final int THREE_VALUE = 3;
    public static final int FOUR_VALUE = 4;
    public static final int FIVE_VALUE = 5;
    public static final int SIX_VALUE = 6;
    public static final int SEVEN_VALUE = 7;
    public static final int EIGHT_VALUE = 8;
    public static final int NINE_VALUE = 9;
    public static final int TEN_VALUE = 10;
    public static final int ELEVEN_VALUE = 11;
    public static final int TWELVE_VALUE = 12;

    public static final PayPlanCycleEnum one = new PayPlanCycleEnum("one", ONE_VALUE);
    public static final PayPlanCycleEnum two = new PayPlanCycleEnum("two", TWO_VALUE);
    public static final PayPlanCycleEnum three = new PayPlanCycleEnum("three", THREE_VALUE);
    public static final PayPlanCycleEnum four = new PayPlanCycleEnum("four", FOUR_VALUE);
    public static final PayPlanCycleEnum five = new PayPlanCycleEnum("five", FIVE_VALUE);
    public static final PayPlanCycleEnum six = new PayPlanCycleEnum("six", SIX_VALUE);
    public static final PayPlanCycleEnum seven = new PayPlanCycleEnum("seven", SEVEN_VALUE);
    public static final PayPlanCycleEnum eight = new PayPlanCycleEnum("eight", EIGHT_VALUE);
    public static final PayPlanCycleEnum nine = new PayPlanCycleEnum("nine", NINE_VALUE);
    public static final PayPlanCycleEnum ten = new PayPlanCycleEnum("ten", TEN_VALUE);
    public static final PayPlanCycleEnum eleven = new PayPlanCycleEnum("eleven", ELEVEN_VALUE);
    public static final PayPlanCycleEnum twelve = new PayPlanCycleEnum("twelve", TWELVE_VALUE);

    /**
     * construct function
     * @param integer payPlanCycleEnum
     */
    private PayPlanCycleEnum(String name, int payPlanCycleEnum)
    {
        super(name, payPlanCycleEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PayPlanCycleEnum getEnum(String payPlanCycleEnum)
    {
        return (PayPlanCycleEnum)getEnum(PayPlanCycleEnum.class, payPlanCycleEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static PayPlanCycleEnum getEnum(int payPlanCycleEnum)
    {
        return (PayPlanCycleEnum)getEnum(PayPlanCycleEnum.class, payPlanCycleEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PayPlanCycleEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PayPlanCycleEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PayPlanCycleEnum.class);
    }
}