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
public class QueryFieldConditionEnum extends StringEnum
{
    public static final String NUM_QUERY_VALUE = "NUM";
    public static final String NAME_QUERY_VALUE = "NAME";
    public static final String NUM_NAME_QUEYR_VALUE = "NUM_NAME";

    public static final QueryFieldConditionEnum NUM_QUERY = new QueryFieldConditionEnum("NUM_QUERY", NUM_QUERY_VALUE);
    public static final QueryFieldConditionEnum NAME_QUERY = new QueryFieldConditionEnum("NAME_QUERY", NAME_QUERY_VALUE);
    public static final QueryFieldConditionEnum NUM_NAME_QUEYR = new QueryFieldConditionEnum("NUM_NAME_QUEYR", NUM_NAME_QUEYR_VALUE);

    /**
     * construct function
     * @param String queryFieldConditionEnum
     */
    private QueryFieldConditionEnum(String name, String queryFieldConditionEnum)
    {
        super(name, queryFieldConditionEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static QueryFieldConditionEnum getEnum(String queryFieldConditionEnum)
    {
        return (QueryFieldConditionEnum)getEnum(QueryFieldConditionEnum.class, queryFieldConditionEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(QueryFieldConditionEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(QueryFieldConditionEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(QueryFieldConditionEnum.class);
    }
}