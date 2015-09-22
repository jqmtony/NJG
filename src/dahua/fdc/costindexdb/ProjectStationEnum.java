/**
 * output package name
 */
package com.kingdee.eas.fdc.costindexdb;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class ProjectStationEnum extends StringEnum
{
    public static final String RUNING_VALUE = "10";//alias=正在进行
    public static final String ENDING_VALUE = "20";//alias=项目结算

    public static final ProjectStationEnum runing = new ProjectStationEnum("runing", RUNING_VALUE);
    public static final ProjectStationEnum ending = new ProjectStationEnum("ending", ENDING_VALUE);

    /**
     * construct function
     * @param String projectStationEnum
     */
    private ProjectStationEnum(String name, String projectStationEnum)
    {
        super(name, projectStationEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ProjectStationEnum getEnum(String projectStationEnum)
    {
        return (ProjectStationEnum)getEnum(ProjectStationEnum.class, projectStationEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ProjectStationEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ProjectStationEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ProjectStationEnum.class);
    }
}