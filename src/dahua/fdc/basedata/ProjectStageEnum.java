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
public class ProjectStageEnum extends StringEnum
{
    public static final String RESEARCH_VALUE = "RESEARCH";
    public static final String AIMCOST_VALUE = "AIMCOST";
    public static final String DYNCOST_VALUE = "DYNCOST";

    public static final ProjectStageEnum RESEARCH = new ProjectStageEnum("RESEARCH", RESEARCH_VALUE);
    public static final ProjectStageEnum AIMCOST = new ProjectStageEnum("AIMCOST", AIMCOST_VALUE);
    public static final ProjectStageEnum DYNCOST = new ProjectStageEnum("DYNCOST", DYNCOST_VALUE);

    /**
     * construct function
     * @param String projectStageEnum
     */
    private ProjectStageEnum(String name, String projectStageEnum)
    {
        super(name, projectStageEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ProjectStageEnum getEnum(String projectStageEnum)
    {
        return (ProjectStageEnum)getEnum(ProjectStageEnum.class, projectStageEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ProjectStageEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ProjectStageEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ProjectStageEnum.class);
    }
}