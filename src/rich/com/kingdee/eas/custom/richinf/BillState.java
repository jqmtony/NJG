/**
 * output package name
 */
package com.kingdee.eas.custom.richinf;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class BillState extends StringEnum
{
    public static final String SAVE_VALUE = "SAVE";//alias=保存
    public static final String SUBMIT_VALUE = "SUBMIT";//alias=提交
    public static final String AUDIT_VALUE = "AUDIT";//alias=已审核

    public static final BillState SAVE = new BillState("SAVE", SAVE_VALUE);
    public static final BillState SUBMIT = new BillState("SUBMIT", SUBMIT_VALUE);
    public static final BillState AUDIT = new BillState("AUDIT", AUDIT_VALUE);

    /**
     * construct function
     * @param String billState
     */
    private BillState(String name, String billState)
    {
        super(name, billState);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BillState getEnum(String billState)
    {
        return (BillState)getEnum(BillState.class, billState);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BillState.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BillState.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BillState.class);
    }
}