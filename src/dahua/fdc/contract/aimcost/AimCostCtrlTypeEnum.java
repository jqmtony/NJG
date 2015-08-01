/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class AimCostCtrlTypeEnum extends IntEnum
{
    public static final int CONTROL_HIGH_VALUE = 100;
    public static final int CONTROL_LOW_VALUE = 200;

    public static final AimCostCtrlTypeEnum CONTROL_HIGH = new AimCostCtrlTypeEnum("CONTROL_HIGH", CONTROL_HIGH_VALUE);
    public static final AimCostCtrlTypeEnum CONTROL_LOW = new AimCostCtrlTypeEnum("CONTROL_LOW", CONTROL_LOW_VALUE);

    /**
     * construct function
     * @param integer aimCostCtrlTypeEnum
     */
    private AimCostCtrlTypeEnum(String name, int aimCostCtrlTypeEnum)
    {
        super(name, aimCostCtrlTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static AimCostCtrlTypeEnum getEnum(String aimCostCtrlTypeEnum)
    {
        return (AimCostCtrlTypeEnum)getEnum(AimCostCtrlTypeEnum.class, aimCostCtrlTypeEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static AimCostCtrlTypeEnum getEnum(int aimCostCtrlTypeEnum)
    {
        return (AimCostCtrlTypeEnum)getEnum(AimCostCtrlTypeEnum.class, aimCostCtrlTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(AimCostCtrlTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(AimCostCtrlTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(AimCostCtrlTypeEnum.class);
    }
}