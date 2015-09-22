/**
 * output package name
 */
package com.kingdee.eas.fdc.costindexdb;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class ContractStationEnum extends StringEnum
{
    public static final String CONTRACTSIGN_VALUE = "10";//alias=合同签订
    public static final String CONTRACTCHANGE_VALUE = "20";//alias=合同变更
    public static final String CONTRACTEND_VALUE = "30";//alias=合同结算
    public static final String PROJECTEND_VALUE = "40";//alias=项目结算

    public static final ContractStationEnum contractSign = new ContractStationEnum("contractSign", CONTRACTSIGN_VALUE);
    public static final ContractStationEnum contractChange = new ContractStationEnum("contractChange", CONTRACTCHANGE_VALUE);
    public static final ContractStationEnum contractEnd = new ContractStationEnum("contractEnd", CONTRACTEND_VALUE);
    public static final ContractStationEnum projectEnd = new ContractStationEnum("projectEnd", PROJECTEND_VALUE);

    /**
     * construct function
     * @param String contractStationEnum
     */
    private ContractStationEnum(String name, String contractStationEnum)
    {
        super(name, contractStationEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ContractStationEnum getEnum(String contractStationEnum)
    {
        return (ContractStationEnum)getEnum(ContractStationEnum.class, contractStationEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ContractStationEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ContractStationEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ContractStationEnum.class);
    }
}