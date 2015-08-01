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
public class ProjectIndexVerTypeEnum extends StringEnum
{
    public static final String AIMCOSTAREA_VALUE = "1AIMCOSTAREA";
    public static final String PRESELLAREA_VALUE = "2PRESELLAREA";
    public static final String COMPLETEAREA_VALUE = "3COMPLETEAREA";

    public static final ProjectIndexVerTypeEnum AIMCOSTAREA = new ProjectIndexVerTypeEnum("AIMCOSTAREA", AIMCOSTAREA_VALUE);
    public static final ProjectIndexVerTypeEnum PRESELLAREA = new ProjectIndexVerTypeEnum("PRESELLAREA", PRESELLAREA_VALUE);
    public static final ProjectIndexVerTypeEnum COMPLETEAREA = new ProjectIndexVerTypeEnum("COMPLETEAREA", COMPLETEAREA_VALUE);

    /**
     * construct function
     * @param String projectIndexVerTypeEnum
     */
    private ProjectIndexVerTypeEnum(String name, String projectIndexVerTypeEnum)
    {
        super(name, projectIndexVerTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ProjectIndexVerTypeEnum getEnum(String projectIndexVerTypeEnum)
    {
        return (ProjectIndexVerTypeEnum)getEnum(ProjectIndexVerTypeEnum.class, projectIndexVerTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ProjectIndexVerTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ProjectIndexVerTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ProjectIndexVerTypeEnum.class);
    }
}