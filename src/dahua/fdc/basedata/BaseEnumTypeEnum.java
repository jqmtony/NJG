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
public class BaseEnumTypeEnum extends StringEnum
{
    public static final String BILLTYPE_VALUE = "1";
    public static final String BASEDATATYPE_VALUE = "2";
    public static final String BASEENUMTYPE_VALUE = "3";
    public static final String CONTRACTPROPERT_VALUE = "4";
    public static final String COSTPROPERTY_VALUE = "5";
    public static final String CONTRACTSOURCE_VALUE = "6";
    public static final String GRAPHCOUNT_VALUE = "7";
    public static final String PUTOUTTYPE_VALUE = "8";
    public static final String DEDUCTMODE_VALUE = "9";

    public static final BaseEnumTypeEnum BillType = new BaseEnumTypeEnum("BillType", BILLTYPE_VALUE);
    public static final BaseEnumTypeEnum BaseDataType = new BaseEnumTypeEnum("BaseDataType", BASEDATATYPE_VALUE);
    public static final BaseEnumTypeEnum BaseEnumType = new BaseEnumTypeEnum("BaseEnumType", BASEENUMTYPE_VALUE);
    public static final BaseEnumTypeEnum ContractPropert = new BaseEnumTypeEnum("ContractPropert", CONTRACTPROPERT_VALUE);
    public static final BaseEnumTypeEnum CostProperty = new BaseEnumTypeEnum("CostProperty", COSTPROPERTY_VALUE);
    public static final BaseEnumTypeEnum ContractSource = new BaseEnumTypeEnum("ContractSource", CONTRACTSOURCE_VALUE);
    public static final BaseEnumTypeEnum GraphCount = new BaseEnumTypeEnum("GraphCount", GRAPHCOUNT_VALUE);
    public static final BaseEnumTypeEnum PutOutType = new BaseEnumTypeEnum("PutOutType", PUTOUTTYPE_VALUE);
    public static final BaseEnumTypeEnum DeductMode = new BaseEnumTypeEnum("DeductMode", DEDUCTMODE_VALUE);

    /**
     * construct function
     * @param String baseEnumTypeEnum
     */
    private BaseEnumTypeEnum(String name, String baseEnumTypeEnum)
    {
        super(name, baseEnumTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BaseEnumTypeEnum getEnum(String baseEnumTypeEnum)
    {
        return (BaseEnumTypeEnum)getEnum(BaseEnumTypeEnum.class, baseEnumTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BaseEnumTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BaseEnumTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BaseEnumTypeEnum.class);
    }
}