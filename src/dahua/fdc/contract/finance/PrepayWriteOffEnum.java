/**
 * output package name
 */
package com.kingdee.eas.fdc.finance;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class PrepayWriteOffEnum extends IntEnum
{
    public static final int ONCE_VALUE = 1;
    public static final int BATCH_VALUE = 2;

    public static final PrepayWriteOffEnum ONCE = new PrepayWriteOffEnum("ONCE", ONCE_VALUE);
    public static final PrepayWriteOffEnum BATCH = new PrepayWriteOffEnum("BATCH", BATCH_VALUE);

    /**
     * construct function
     * @param integer prepayWriteOffEnum
     */
    private PrepayWriteOffEnum(String name, int prepayWriteOffEnum)
    {
        super(name, prepayWriteOffEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PrepayWriteOffEnum getEnum(String prepayWriteOffEnum)
    {
        return (PrepayWriteOffEnum)getEnum(PrepayWriteOffEnum.class, prepayWriteOffEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static PrepayWriteOffEnum getEnum(int prepayWriteOffEnum)
    {
        return (PrepayWriteOffEnum)getEnum(PrepayWriteOffEnum.class, prepayWriteOffEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PrepayWriteOffEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PrepayWriteOffEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PrepayWriteOffEnum.class);
    }
}