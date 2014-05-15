/**
 * output package name
 */
package com.kingdee.eas.port.pm.base.coms;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class PlanTypeEnum extends StringEnum
{
    public static final String YEARPLAN_VALUE = "10";//alias=年度计划
    public static final String ADDPLAN_VALUE = "20";//alias=追加计划

    public static final PlanTypeEnum yearPlan = new PlanTypeEnum("yearPlan", YEARPLAN_VALUE);
    public static final PlanTypeEnum addPlan = new PlanTypeEnum("addPlan", ADDPLAN_VALUE);

    /**
     * construct function
     * @param String planTypeEnum
     */
    private PlanTypeEnum(String name, String planTypeEnum)
    {
        super(name, planTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PlanTypeEnum getEnum(String planTypeEnum)
    {
        return (PlanTypeEnum)getEnum(PlanTypeEnum.class, planTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PlanTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PlanTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PlanTypeEnum.class);
    }
}