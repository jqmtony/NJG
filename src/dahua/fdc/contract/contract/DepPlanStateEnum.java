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
public class DepPlanStateEnum extends StringEnum
{
    public static final String NOPLAN_VALUE = "0";
    public static final String INPLAN_VALUE = "1";
    public static final String OUTPLAN_VALUE = "2";

    public static final DepPlanStateEnum noPlan = new DepPlanStateEnum("noPlan", NOPLAN_VALUE);
    public static final DepPlanStateEnum inPlan = new DepPlanStateEnum("inPlan", INPLAN_VALUE);
    public static final DepPlanStateEnum outPlan = new DepPlanStateEnum("outPlan", OUTPLAN_VALUE);

    /**
     * construct function
     * @param String depPlanStateEnum
     */
    private DepPlanStateEnum(String name, String depPlanStateEnum)
    {
        super(name, depPlanStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static DepPlanStateEnum getEnum(String depPlanStateEnum)
    {
        return (DepPlanStateEnum)getEnum(DepPlanStateEnum.class, depPlanStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(DepPlanStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(DepPlanStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(DepPlanStateEnum.class);
    }
}