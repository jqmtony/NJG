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
public class TaxPayerTypeEnum extends StringEnum
{
    public static final String ONE_VALUE = "111";
    public static final String TWO_VALUE = "222";

    public static final TaxPayerTypeEnum one = new TaxPayerTypeEnum("one", ONE_VALUE);
    public static final TaxPayerTypeEnum two = new TaxPayerTypeEnum("two", TWO_VALUE);

    /**
     * construct function
     * @param String taxPayerTypeEnum
     */
    private TaxPayerTypeEnum(String name, String taxPayerTypeEnum)
    {
        super(name, taxPayerTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TaxPayerTypeEnum getEnum(String taxPayerTypeEnum)
    {
        return (TaxPayerTypeEnum)getEnum(TaxPayerTypeEnum.class, taxPayerTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TaxPayerTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TaxPayerTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TaxPayerTypeEnum.class);
    }
}