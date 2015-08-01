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
public class CostSplitBillTypeEnum extends StringEnum
{
    public static final String CONTRACTSPLIT_VALUE = "CONTRACTSPLIT";
    public static final String CNTRCHANGESPLIT_VALUE = "CNTRCHANGESPLIT";
    public static final String SETTLEMENTSPLIT_VALUE = "SETTLEMENTSPLIT";
    public static final String PAYMENTSPLIT_VALUE = "PAYMENTSPLIT";
    public static final String NOTEXTCONSPLIT_VALUE = "NOTEXTCONSPLIT";

    public static final CostSplitBillTypeEnum CONTRACTSPLIT = new CostSplitBillTypeEnum("CONTRACTSPLIT", CONTRACTSPLIT_VALUE);
    public static final CostSplitBillTypeEnum CNTRCHANGESPLIT = new CostSplitBillTypeEnum("CNTRCHANGESPLIT", CNTRCHANGESPLIT_VALUE);
    public static final CostSplitBillTypeEnum SETTLEMENTSPLIT = new CostSplitBillTypeEnum("SETTLEMENTSPLIT", SETTLEMENTSPLIT_VALUE);
    public static final CostSplitBillTypeEnum PAYMENTSPLIT = new CostSplitBillTypeEnum("PAYMENTSPLIT", PAYMENTSPLIT_VALUE);
    public static final CostSplitBillTypeEnum NOTEXTCONSPLIT = new CostSplitBillTypeEnum("NOTEXTCONSPLIT", NOTEXTCONSPLIT_VALUE);

    /**
     * construct function
     * @param String costSplitBillTypeEnum
     */
    private CostSplitBillTypeEnum(String name, String costSplitBillTypeEnum)
    {
        super(name, costSplitBillTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CostSplitBillTypeEnum getEnum(String costSplitBillTypeEnum)
    {
        return (CostSplitBillTypeEnum)getEnum(CostSplitBillTypeEnum.class, costSplitBillTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CostSplitBillTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CostSplitBillTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CostSplitBillTypeEnum.class);
    }
}