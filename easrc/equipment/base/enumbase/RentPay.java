/**
 * output package name
 */
package com.kingdee.eas.port.equipment.base.enumbase;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class RentPay extends StringEnum
{
    public static final String YEARRENT_VALUE = "0";//alias=年缴
    public static final String QUATRENT_VALUE = "1";//alias=季缴
    public static final String MONTHRENT_VALUE = "2";//alias=月缴
    public static final String DAYRENT_VALUE = "3";//alias=日缴
    public static final String ONERENT_VALUE = "4";//alias=一次性缴清
    public static final String IRREGULAR_VALUE = "5";//alias=不定期

    public static final RentPay YEARRENT = new RentPay("YEARRENT", YEARRENT_VALUE);
    public static final RentPay QUATRENT = new RentPay("QUATRENT", QUATRENT_VALUE);
    public static final RentPay MONTHRENT = new RentPay("MONTHRENT", MONTHRENT_VALUE);
    public static final RentPay DAYRENT = new RentPay("DAYRENT", DAYRENT_VALUE);
    public static final RentPay ONERENT = new RentPay("ONERENT", ONERENT_VALUE);
    public static final RentPay IRREGULAR = new RentPay("IRREGULAR", IRREGULAR_VALUE);

    /**
     * construct function
     * @param String rentPay
     */
    private RentPay(String name, String rentPay)
    {
        super(name, rentPay);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static RentPay getEnum(String rentPay)
    {
        return (RentPay)getEnum(RentPay.class, rentPay);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(RentPay.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(RentPay.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(RentPay.class);
    }
}