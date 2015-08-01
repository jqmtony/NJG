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
public class TargetExpandTypeEnum extends StringEnum
{
    public static final String NOEXPAND_VALUE = "0";//alias=不扩展
    public static final String COLUMN_VALUE = "1";//alias=柱状图
    public static final String PROPORTIONDISTRIBUTION_VALUE = "2";//alias=比例分布图
    public static final String DROPDOWNSHEET_VALUE = "3";//alias=下拉数据表
    public static final String CONTRAST_VALUE = "4";//alias=对比
    public static final String TEXT_VALUE = "5";//alias=文字
    public static final String TWODIMENSIONALTABLE_VALUE = "6";//alias=二维表
    public static final String PLAN_VALUE = "9";//alias=规划图

    public static final TargetExpandTypeEnum NoExpand = new TargetExpandTypeEnum("NoExpand", NOEXPAND_VALUE);
    public static final TargetExpandTypeEnum Column = new TargetExpandTypeEnum("Column", COLUMN_VALUE);
    public static final TargetExpandTypeEnum ProportionDistribution = new TargetExpandTypeEnum("ProportionDistribution", PROPORTIONDISTRIBUTION_VALUE);
    public static final TargetExpandTypeEnum DropdownSheet = new TargetExpandTypeEnum("DropdownSheet", DROPDOWNSHEET_VALUE);
    public static final TargetExpandTypeEnum Contrast = new TargetExpandTypeEnum("Contrast", CONTRAST_VALUE);
    public static final TargetExpandTypeEnum Text = new TargetExpandTypeEnum("Text", TEXT_VALUE);
    public static final TargetExpandTypeEnum TwoDimensionalTable = new TargetExpandTypeEnum("TwoDimensionalTable", TWODIMENSIONALTABLE_VALUE);
    public static final TargetExpandTypeEnum Plan = new TargetExpandTypeEnum("Plan", PLAN_VALUE);

    /**
     * construct function
     * @param String targetExpandTypeEnum
     */
    private TargetExpandTypeEnum(String name, String targetExpandTypeEnum)
    {
        super(name, targetExpandTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TargetExpandTypeEnum getEnum(String targetExpandTypeEnum)
    {
        return (TargetExpandTypeEnum)getEnum(TargetExpandTypeEnum.class, targetExpandTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TargetExpandTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TargetExpandTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TargetExpandTypeEnum.class);
    }
}