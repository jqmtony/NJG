/**
 * output package name
 */
package com.kingdee.eas.fdc.costindexdb.database;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class BuildSplitDataType extends StringEnum
{
    public static final String BASEPOINT_VALUE = "basePoint";//alias=基本要素
    public static final String SINGLEPOINT_VALUE = "singlePoint";//alias=单项要素
    public static final String CONTRACT_VALUE = "contract";//alias=合同
    public static final String PROFESSPOINT_VALUE = "professPoint";//alias=专业要素

    public static final BuildSplitDataType basePoint = new BuildSplitDataType("basePoint", BASEPOINT_VALUE);
    public static final BuildSplitDataType singlePoint = new BuildSplitDataType("singlePoint", SINGLEPOINT_VALUE);
    public static final BuildSplitDataType contract = new BuildSplitDataType("contract", CONTRACT_VALUE);
    public static final BuildSplitDataType professPoint = new BuildSplitDataType("professPoint", PROFESSPOINT_VALUE);

    /**
     * construct function
     * @param String buildSplitDataType
     */
    private BuildSplitDataType(String name, String buildSplitDataType)
    {
        super(name, buildSplitDataType);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BuildSplitDataType getEnum(String buildSplitDataType)
    {
        return (BuildSplitDataType)getEnum(BuildSplitDataType.class, buildSplitDataType);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BuildSplitDataType.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BuildSplitDataType.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BuildSplitDataType.class);
    }
}