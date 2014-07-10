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
    public static final String YEARRENT_VALUE = "0";//alias=���
    public static final String QUATRENT_VALUE = "1";//alias=����
    public static final String MONTHRENT_VALUE = "2";//alias=�½�
    public static final String DAYRENT_VALUE = "3";//alias=�ս�
    public static final String ONERENT_VALUE = "4";//alias=һ���Խ���
    public static final String IRREGULAR_VALUE = "5";//alias=������

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