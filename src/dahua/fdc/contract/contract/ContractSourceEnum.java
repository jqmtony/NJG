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
public class ContractSourceEnum extends StringEnum
{
    public static final String TRUST_VALUE = "TRUST";
    public static final String INVITE_VALUE = "INVITE";
    public static final String DISCUSS_VALUE = "DISCUSS";
    public static final String COOP_VALUE = "COOP";

    public static final ContractSourceEnum TRUST = new ContractSourceEnum("TRUST", TRUST_VALUE);
    public static final ContractSourceEnum INVITE = new ContractSourceEnum("INVITE", INVITE_VALUE);
    public static final ContractSourceEnum DISCUSS = new ContractSourceEnum("DISCUSS", DISCUSS_VALUE);
    public static final ContractSourceEnum COOP = new ContractSourceEnum("COOP", COOP_VALUE);

    /**
     * construct function
     * @param String contractSourceEnum
     */
    private ContractSourceEnum(String name, String contractSourceEnum)
    {
        super(name, contractSourceEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ContractSourceEnum getEnum(String contractSourceEnum)
    {
        return (ContractSourceEnum)getEnum(ContractSourceEnum.class, contractSourceEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ContractSourceEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ContractSourceEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ContractSourceEnum.class);
    }
}