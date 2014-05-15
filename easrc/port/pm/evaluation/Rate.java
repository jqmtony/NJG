/**
 * output package name
 */
package com.kingdee.eas.port.pm.evaluation;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class Rate extends StringEnum
{
    public static final String SUCCESS_VALUE = "1";//alias=成功
    public static final String BASICSUCCESS_VALUE = "2";//alias=基本成功
    public static final String PARTSUCCESS_VALUE = "3";//alias=部分成功
    public static final String UNSUCCESSFUL_VALUE = "4";//alias=不成功
    public static final String FAILURE_VALUE = "5";//alias=失败

    public static final Rate success = new Rate("success", SUCCESS_VALUE);
    public static final Rate basicSuccess = new Rate("basicSuccess", BASICSUCCESS_VALUE);
    public static final Rate partSuccess = new Rate("partSuccess", PARTSUCCESS_VALUE);
    public static final Rate unsuccessful = new Rate("unsuccessful", UNSUCCESSFUL_VALUE);
    public static final Rate failure = new Rate("failure", FAILURE_VALUE);

    /**
     * construct function
     * @param String rate
     */
    private Rate(String name, String rate)
    {
        super(name, rate);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static Rate getEnum(String rate)
    {
        return (Rate)getEnum(Rate.class, rate);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(Rate.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(Rate.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(Rate.class);
    }
}