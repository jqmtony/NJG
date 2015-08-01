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
public class ContractSecondTypeEnum extends StringEnum
{
    public static final String OLD_VALUE = "OLD";
    public static final String DIRECT_VALUE = "DIRECT";
    public static final String THREE_PARTY_VALUE = "THREE_PARTY";
    public static final String SUPPLY_VALUE = "SUPPLY";

    public static final ContractSecondTypeEnum OLD = new ContractSecondTypeEnum("OLD", OLD_VALUE);
    public static final ContractSecondTypeEnum DIRECT = new ContractSecondTypeEnum("DIRECT", DIRECT_VALUE);
    public static final ContractSecondTypeEnum THREE_PARTY = new ContractSecondTypeEnum("THREE_PARTY", THREE_PARTY_VALUE);
    public static final ContractSecondTypeEnum SUPPLY = new ContractSecondTypeEnum("SUPPLY", SUPPLY_VALUE);

    /**
     * construct function
     * @param String contractSecondTypeEnum
     */
    private ContractSecondTypeEnum(String name, String contractSecondTypeEnum)
    {
        super(name, contractSecondTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ContractSecondTypeEnum getEnum(String contractSecondTypeEnum)
    {
        return (ContractSecondTypeEnum)getEnum(ContractSecondTypeEnum.class, contractSecondTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ContractSecondTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ContractSecondTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ContractSecondTypeEnum.class);
    }
}