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
    public static final String YEARPLAN_VALUE = "10";//alias=��ȼƻ�
    public static final String ADDPLAN_VALUE = "20";//alias=������Ŀ�ƻ�
    public static final String CHANGE_VALUE = "30";//alias=�����Ŀ�ƻ�
    public static final String ADJUST_VALUE = "40";//alias=��Ŀ�����ƻ�
    public static final String COMPANYPLAN_VALUE = "50";//alias=������Ŀ

    public static final PlanTypeEnum yearPlan = new PlanTypeEnum("yearPlan", YEARPLAN_VALUE);
    public static final PlanTypeEnum addPlan = new PlanTypeEnum("addPlan", ADDPLAN_VALUE);
    public static final PlanTypeEnum change = new PlanTypeEnum("change", CHANGE_VALUE);
    public static final PlanTypeEnum adjust = new PlanTypeEnum("adjust", ADJUST_VALUE);
    public static final PlanTypeEnum companyPlan = new PlanTypeEnum("companyPlan", COMPANYPLAN_VALUE);

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