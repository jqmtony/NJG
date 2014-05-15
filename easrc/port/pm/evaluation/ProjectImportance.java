/**
 * output package name
 */
package com.kingdee.eas.port.pm.evaluation;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class ProjectImportance extends StringEnum
{
    public static final String IMPORTANT_VALUE = "1";//alias=重要
    public static final String LESSIMPORTANT_VALUE = "2";//alias=次重要
    public static final String UNIMPORTANT_VALUE = "3";//alias=不重要

    public static final ProjectImportance important = new ProjectImportance("important", IMPORTANT_VALUE);
    public static final ProjectImportance lessImportant = new ProjectImportance("lessImportant", LESSIMPORTANT_VALUE);
    public static final ProjectImportance unimportant = new ProjectImportance("unimportant", UNIMPORTANT_VALUE);

    /**
     * construct function
     * @param String projectImportance
     */
    private ProjectImportance(String name, String projectImportance)
    {
        super(name, projectImportance);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ProjectImportance getEnum(String projectImportance)
    {
        return (ProjectImportance)getEnum(ProjectImportance.class, projectImportance);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ProjectImportance.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ProjectImportance.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ProjectImportance.class);
    }
}