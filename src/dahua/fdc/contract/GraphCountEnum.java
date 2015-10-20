/**
 * output package name
 */
package com.kingdee.eas.fdc.contract;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class GraphCountEnum extends StringEnum
{
    public static final String ELECTFILE_VALUE = "1ElectFile";//alias=电子文件
    public static final String PAPERFILE_VALUE = "2PaperFile";//alias=纸质文件
    public static final String NOFILE_VALUE = "3NoFile";//alias=无文件

    public static final GraphCountEnum ElectFile = new GraphCountEnum("ElectFile", ELECTFILE_VALUE);
    public static final GraphCountEnum PaperFile = new GraphCountEnum("PaperFile", PAPERFILE_VALUE);
    public static final GraphCountEnum NoFile = new GraphCountEnum("NoFile", NOFILE_VALUE);

    /**
     * construct function
     * @param String graphCountEnum
     */
    private GraphCountEnum(String name, String graphCountEnum)
    {
        super(name, graphCountEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static GraphCountEnum getEnum(String graphCountEnum)
    {
        return (GraphCountEnum)getEnum(GraphCountEnum.class, graphCountEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(GraphCountEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(GraphCountEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(GraphCountEnum.class);
    }
}