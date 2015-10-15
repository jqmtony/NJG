/**
 * output package name
 */
package com.kingdee.eas.fdc.finance;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class ProjectPlanDetialTypeEnum extends StringEnum
{
    public static final String YEAR_VALUE = "YEAR";//alias=Äê
    public static final String MONTH_VALUE = "MONTH";//alias=ÔÂ
    public static final String QUARTER_VALUE = "QUARTER";//alias=¼¾

    public static final ProjectPlanDetialTypeEnum YEAR = new ProjectPlanDetialTypeEnum("YEAR", YEAR_VALUE);
    public static final ProjectPlanDetialTypeEnum MONTH = new ProjectPlanDetialTypeEnum("MONTH", MONTH_VALUE);
    public static final ProjectPlanDetialTypeEnum QUARTER = new ProjectPlanDetialTypeEnum("QUARTER", QUARTER_VALUE);

    /**
     * construct function
     * @param String projectPlanDetialTypeEnum
     */
    private ProjectPlanDetialTypeEnum(String name, String projectPlanDetialTypeEnum)
    {
        super(name, projectPlanDetialTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ProjectPlanDetialTypeEnum getEnum(String projectPlanDetialTypeEnum)
    {
        return (ProjectPlanDetialTypeEnum)getEnum(ProjectPlanDetialTypeEnum.class, projectPlanDetialTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ProjectPlanDetialTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ProjectPlanDetialTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ProjectPlanDetialTypeEnum.class);
    }
}