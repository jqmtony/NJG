/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class PriceWay extends StringEnum
{
    public static final String GDZJ_VALUE = "GDZJ";//alias=固定总价
    public static final String GDDJ_VALUE = "GDDJ";//alias=固定单价
    public static final String ZDZJ_VALUE = "ZDZJ";//alias=暂订总价

    public static final PriceWay GDZJ = new PriceWay("GDZJ", GDZJ_VALUE);
    public static final PriceWay GDDJ = new PriceWay("GDDJ", GDDJ_VALUE);
    public static final PriceWay ZDZJ = new PriceWay("ZDZJ", ZDZJ_VALUE);

    /**
     * construct function
     * @param String priceWay
     */
    private PriceWay(String name, String priceWay)
    {
        super(name, priceWay);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PriceWay getEnum(String priceWay)
    {
        return (PriceWay)getEnum(PriceWay.class, priceWay);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PriceWay.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PriceWay.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PriceWay.class);
    }
}