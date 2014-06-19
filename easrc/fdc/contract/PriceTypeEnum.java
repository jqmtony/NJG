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
public class PriceTypeEnum extends StringEnum
{
    public static final String CHARGE_VALUE = "CHARGE";//alias=费率
    public static final String LIST_VALUE = "LIST";//alias=清单

    public static final PriceTypeEnum CHARGE = new PriceTypeEnum("CHARGE", CHARGE_VALUE);
    public static final PriceTypeEnum LIST = new PriceTypeEnum("LIST", LIST_VALUE);

    /**
     * construct function
     * @param String priceTypeEnum
     */
    private PriceTypeEnum(String name, String priceTypeEnum)
    {
        super(name, priceTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PriceTypeEnum getEnum(String priceTypeEnum)
    {
        return (PriceTypeEnum)getEnum(PriceTypeEnum.class, priceTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PriceTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PriceTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PriceTypeEnum.class);
    }
}