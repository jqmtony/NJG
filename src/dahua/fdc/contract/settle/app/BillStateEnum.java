/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.settle.app;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class BillStateEnum extends IntEnum
{
    public static final int ADDNEW_VALUE = 10;//alias=新增
    public static final int SAVE_VALUE = 20;//alias=保存
    public static final int SUBMIT_VALUE = 30;//alias=提交
    public static final int AUDIT_VALUE = 40;//alias=审核
    public static final int UNAUDIT_VALUE = 50;//alias=反审核
    public static final int EXPIREINVALID_VALUE = 60;//alias=过期
    public static final int TEMPORARILYSAVED_VALUE = 15;//alias=暂存

    public static final BillStateEnum ADDNEW = new BillStateEnum("ADDNEW", ADDNEW_VALUE);
    public static final BillStateEnum SAVE = new BillStateEnum("SAVE", SAVE_VALUE);
    public static final BillStateEnum SUBMIT = new BillStateEnum("SUBMIT", SUBMIT_VALUE);
    public static final BillStateEnum AUDIT = new BillStateEnum("AUDIT", AUDIT_VALUE);
    public static final BillStateEnum UNAUDIT = new BillStateEnum("UNAUDIT", UNAUDIT_VALUE);
    public static final BillStateEnum EXPIREINVALID = new BillStateEnum("EXPIREINVALID", EXPIREINVALID_VALUE);
    public static final BillStateEnum TEMPORARILYSAVED = new BillStateEnum("TEMPORARILYSAVED", TEMPORARILYSAVED_VALUE);

    /**
     * construct function
     * @param integer billStateEnum
     */
    private BillStateEnum(String name, int billStateEnum)
    {
        super(name, billStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BillStateEnum getEnum(String billStateEnum)
    {
        return (BillStateEnum)getEnum(BillStateEnum.class, billStateEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static BillStateEnum getEnum(int billStateEnum)
    {
        return (BillStateEnum)getEnum(BillStateEnum.class, billStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BillStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BillStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BillStateEnum.class);
    }
}