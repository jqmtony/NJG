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
public class ContractThirdTypeEnum extends StringEnum
{
    public static final String NEW_VALUE = "NEW";
    public static final String STORE_VALUE = "STORE";

    public static final ContractThirdTypeEnum NEW = new ContractThirdTypeEnum("NEW", NEW_VALUE);
    public static final ContractThirdTypeEnum STORE = new ContractThirdTypeEnum("STORE", STORE_VALUE);

    /**
     * construct function
     * @param String contractThirdTypeEnum
     */
    private ContractThirdTypeEnum(String name, String contractThirdTypeEnum)
    {
        super(name, contractThirdTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ContractThirdTypeEnum getEnum(String contractThirdTypeEnum)
    {
        return (ContractThirdTypeEnum)getEnum(ContractThirdTypeEnum.class, contractThirdTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ContractThirdTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ContractThirdTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ContractThirdTypeEnum.class);
    }
}