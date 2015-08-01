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
public class PutOutTypeEnum extends StringEnum
{
    public static final String CASH_VALUE = "Cash";
    public static final String TICKET_VALUE = "Ticket";

    public static final PutOutTypeEnum Cash = new PutOutTypeEnum("Cash", CASH_VALUE);
    public static final PutOutTypeEnum Ticket = new PutOutTypeEnum("Ticket", TICKET_VALUE);

    /**
     * construct function
     * @param String putOutTypeEnum
     */
    private PutOutTypeEnum(String name, String putOutTypeEnum)
    {
        super(name, putOutTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PutOutTypeEnum getEnum(String putOutTypeEnum)
    {
        return (PutOutTypeEnum)getEnum(PutOutTypeEnum.class, putOutTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PutOutTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PutOutTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PutOutTypeEnum.class);
    }
}