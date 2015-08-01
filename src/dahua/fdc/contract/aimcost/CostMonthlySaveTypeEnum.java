/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class CostMonthlySaveTypeEnum extends StringEnum
{
    public static final String COMMON_VALUE = "1COMMON";
    public static final String COSTMONTHLY_VALUE = "2COSTMONTHLY";
    public static final String FINANCEMONTHLY_VALUE = "3FINANCEMONTHLY";
    public static final String AUTOSAVE_VALUE = "4AUTOSAVE";
    public static final String ONLYONE_VALUE = "5ONLYONE";
    // 普通保存
    public static final CostMonthlySaveTypeEnum COMMON = new CostMonthlySaveTypeEnum("COMMON", COMMON_VALUE);
    // 成本月结保存
    public static final CostMonthlySaveTypeEnum COSTMONTHLY = new CostMonthlySaveTypeEnum("COSTMONTHLY", COSTMONTHLY_VALUE);
    // 财务月结保存
    public static final CostMonthlySaveTypeEnum FINANCEMONTHLY = new CostMonthlySaveTypeEnum("FINANCEMONTHLY", FINANCEMONTHLY_VALUE);
    // 事务保存，一月一份
    public static final CostMonthlySaveTypeEnum AUTOSAVE = new CostMonthlySaveTypeEnum("AUTOSAVE", AUTOSAVE_VALUE);
    // 仅一份
	public static final CostMonthlySaveTypeEnum ONLYONE = new CostMonthlySaveTypeEnum(
			"ONLYONE", ONLYONE_VALUE);

    /**
     * construct function
     * @param String costMonthlySaveTypeEnum
     */
    private CostMonthlySaveTypeEnum(String name, String costMonthlySaveTypeEnum)
    {
        super(name, costMonthlySaveTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CostMonthlySaveTypeEnum getEnum(String costMonthlySaveTypeEnum)
    {
        return (CostMonthlySaveTypeEnum)getEnum(CostMonthlySaveTypeEnum.class, costMonthlySaveTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CostMonthlySaveTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CostMonthlySaveTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CostMonthlySaveTypeEnum.class);
    }
}