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
public class SendContWay extends StringEnum
{
    public static final String GKZB_VALUE = "GKZB";//alias=公开招标
    public static final String YQZB_VALUE = "YQZB";//alias=邀请招标
    public static final String ZJWT_VALUE = "ZJWT";//alias=直接委托
    public static final String ZLHZ_VALUE = "ZLHZ";//alias=战略合作

    public static final SendContWay GKZB = new SendContWay("GKZB", GKZB_VALUE);
    public static final SendContWay YQZB = new SendContWay("YQZB", YQZB_VALUE);
    public static final SendContWay ZJWT = new SendContWay("ZJWT", ZJWT_VALUE);
    public static final SendContWay ZLHZ = new SendContWay("ZLHZ", ZLHZ_VALUE);

    /**
     * construct function
     * @param String sendContWay
     */
    private SendContWay(String name, String sendContWay)
    {
        super(name, sendContWay);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SendContWay getEnum(String sendContWay)
    {
        return (SendContWay)getEnum(SendContWay.class, sendContWay);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SendContWay.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SendContWay.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SendContWay.class);
    }
}