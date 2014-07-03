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
public class TransferType extends StringEnum
{
    public static final String GROUP_VALUE = "1";//alias=集团内租用
    public static final String ASSETSTRAN_VALUE = "2";//alias=资产转移

    public static final TransferType group = new TransferType("group", GROUP_VALUE);
    public static final TransferType assetstran = new TransferType("assetstran", ASSETSTRAN_VALUE);

    /**
     * construct function
     * @param String transferType
     */
    private TransferType(String name, String transferType)
    {
        super(name, transferType);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TransferType getEnum(String transferType)
    {
        return (TransferType)getEnum(TransferType.class, transferType);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TransferType.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TransferType.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TransferType.class);
    }
}