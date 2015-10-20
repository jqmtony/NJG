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
public class ContractExecStateEnum extends StringEnum
{
    public static final String SIGNED_VALUE = "SIGNED";
    public static final String NOTSETTLE_VALUE = "NOTSETTLE";
    public static final String SETTLED_VALUE = "SETTLED";
    public static final String PAYED_VALUE = "PAYED";

    public static final ContractExecStateEnum SIGNED = new ContractExecStateEnum("SIGNED", SIGNED_VALUE);
    public static final ContractExecStateEnum NOTSETTLE = new ContractExecStateEnum("NOTSETTLE", NOTSETTLE_VALUE);
    public static final ContractExecStateEnum SETTLED = new ContractExecStateEnum("SETTLED", SETTLED_VALUE);
    public static final ContractExecStateEnum PAYED = new ContractExecStateEnum("PAYED", PAYED_VALUE);

    /**
     * construct function
     * @param String contractExecStateEnum
     */
    private ContractExecStateEnum(String name, String contractExecStateEnum)
    {
        super(name, contractExecStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ContractExecStateEnum getEnum(String contractExecStateEnum)
    {
        return (ContractExecStateEnum)getEnum(ContractExecStateEnum.class, contractExecStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ContractExecStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ContractExecStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ContractExecStateEnum.class);
    }
}