/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class TargetGroupEnum extends StringEnum
{
    public static final String BASEINFO_VALUE = "1";//alias=基本信息
    public static final String OPERATIONINFO_VALUE = "2";//alias=运营信息
    public static final String INVENTORYANDSALE_VALUE = "3";//alias=库存与销售
    public static final String TARGETSORT_VALUE = "8";//alias=首页指标排序
    public static final String TARGETINPUT_VALUE = "9";//alias=EAS指标录入
    public static final String BASE_INFO_$_EX_VALUE = "001_BASE_INFO_$_EX";//alias=基础信息
    public static final String PROJECT_SCHEDULE_$_EX_VALUE = "002_PROJECT_SCHEDULE_$_EX";//alias=工程进度
    public static final String COST_FINANCE_$_EX_VALUE = "003_COST_FINANCE_$_EX";//alias=成本资金
    public static final String SALE_STOCK_$_EX_VALUE = "004_SALE_STOCK_$_EX";//alias=销售库存
    public static final String OTHER_$_EX_VALUE = "005_OTHER_$_EX";//alias=其它

    public static final TargetGroupEnum BaseInfo = new TargetGroupEnum("BaseInfo", BASEINFO_VALUE);
    public static final TargetGroupEnum OperationInfo = new TargetGroupEnum("OperationInfo", OPERATIONINFO_VALUE);
    public static final TargetGroupEnum InventoryAndSale = new TargetGroupEnum("InventoryAndSale", INVENTORYANDSALE_VALUE);
    public static final TargetGroupEnum TargetSort = new TargetGroupEnum("TargetSort", TARGETSORT_VALUE);
    public static final TargetGroupEnum TargetInput = new TargetGroupEnum("TargetInput", TARGETINPUT_VALUE);
    public static final TargetGroupEnum BASE_INFO_$_EX = new TargetGroupEnum("BASE_INFO_$_EX", BASE_INFO_$_EX_VALUE);
    public static final TargetGroupEnum PROJECT_SCHEDULE_$_EX = new TargetGroupEnum("PROJECT_SCHEDULE_$_EX", PROJECT_SCHEDULE_$_EX_VALUE);
    public static final TargetGroupEnum COST_FINANCE_$_EX = new TargetGroupEnum("COST_FINANCE_$_EX", COST_FINANCE_$_EX_VALUE);
    public static final TargetGroupEnum SALE_STOCK_$_EX = new TargetGroupEnum("SALE_STOCK_$_EX", SALE_STOCK_$_EX_VALUE);
    public static final TargetGroupEnum OTHER_$_EX = new TargetGroupEnum("OTHER_$_EX", OTHER_$_EX_VALUE);

    /**
     * construct function
     * @param String targetGroupEnum
     */
    private TargetGroupEnum(String name, String targetGroupEnum)
    {
        super(name, targetGroupEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TargetGroupEnum getEnum(String targetGroupEnum)
    {
        return (TargetGroupEnum)getEnum(TargetGroupEnum.class, targetGroupEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TargetGroupEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TargetGroupEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TargetGroupEnum.class);
    }
}