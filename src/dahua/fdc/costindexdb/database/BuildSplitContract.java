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
public class BuildSplitContract extends StringEnum
{
    public static final String SIGN_VALUE = "sign";//alias=合同签订
    public static final String CHANGE_VALUE = "change";//alias=合同变更
    public static final String ENDCAL_VALUE = "endCal";//alias=合同结算

    public static final BuildSplitContract sign = new BuildSplitContract("sign", SIGN_VALUE);
    public static final BuildSplitContract change = new BuildSplitContract("change", CHANGE_VALUE);
    public static final BuildSplitContract endCal = new BuildSplitContract("endCal", ENDCAL_VALUE);

    /**
     * construct function
     * @param String buildSplitContract
     */
    private BuildSplitContract(String name, String buildSplitContract)
    {
        super(name, buildSplitContract);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BuildSplitContract getEnum(String buildSplitContract)
    {
        return (BuildSplitContract)getEnum(BuildSplitContract.class, buildSplitContract);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BuildSplitContract.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BuildSplitContract.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BuildSplitContract.class);
    }
}