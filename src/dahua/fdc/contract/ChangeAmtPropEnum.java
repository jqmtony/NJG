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
public class ChangeAmtPropEnum extends StringEnum
{
    public static final String CONFIRMED_PRICE_VALUE = "CONFIRMED_PRICE";
    public static final String UNCONFIRMED_PRICE_VALUE = "UNCONFIRMED_PRICE";

    public static final ChangeAmtPropEnum CONFIRMED_PRICE = new ChangeAmtPropEnum("CONFIRMED_PRICE", CONFIRMED_PRICE_VALUE);
    public static final ChangeAmtPropEnum UNCONFIRMED_PRICE = new ChangeAmtPropEnum("UNCONFIRMED_PRICE", UNCONFIRMED_PRICE_VALUE);

    /**
     * construct function
     * @param String changeAmtPropEnum
     */
    private ChangeAmtPropEnum(String name, String changeAmtPropEnum)
    {
        super(name, changeAmtPropEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ChangeAmtPropEnum getEnum(String changeAmtPropEnum)
    {
        return (ChangeAmtPropEnum)getEnum(ChangeAmtPropEnum.class, changeAmtPropEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ChangeAmtPropEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ChangeAmtPropEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ChangeAmtPropEnum.class);
    }
}