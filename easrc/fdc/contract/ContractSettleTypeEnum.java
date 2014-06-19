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
public class ContractSettleTypeEnum extends StringEnum
{
    public static final String ATYPE_VALUE = "1AType";//alias=A¿‡
    public static final String BTYPE_VALUE = "2BType";//alias=B¿‡
    public static final String CTYPE_VALUE = "3CType";//alias=C¿‡

    public static final ContractSettleTypeEnum AType = new ContractSettleTypeEnum("AType", ATYPE_VALUE);
    public static final ContractSettleTypeEnum BType = new ContractSettleTypeEnum("BType", BTYPE_VALUE);
    public static final ContractSettleTypeEnum CType = new ContractSettleTypeEnum("CType", CTYPE_VALUE);

    /**
     * construct function
     * @param String contractSettleTypeEnum
     */
    private ContractSettleTypeEnum(String name, String contractSettleTypeEnum)
    {
        super(name, contractSettleTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ContractSettleTypeEnum getEnum(String contractSettleTypeEnum)
    {
        return (ContractSettleTypeEnum)getEnum(ContractSettleTypeEnum.class, contractSettleTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ContractSettleTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ContractSettleTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ContractSettleTypeEnum.class);
    }
}