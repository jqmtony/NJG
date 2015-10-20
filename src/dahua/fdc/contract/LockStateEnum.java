/**
 * output package name
 */
package com.kingdee.eas.fdc.contract;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class LockStateEnum extends IntEnum
{
    public static final int LOCK_VALUE = 1;
    public static final int UNLOCK_VALUE = -1;
    public static final int NORMAL_VALUE = 0;

    public static final LockStateEnum LOCK = new LockStateEnum("LOCK", LOCK_VALUE);
    public static final LockStateEnum UNLOCK = new LockStateEnum("UNLOCK", UNLOCK_VALUE);
    public static final LockStateEnum NORMAL = new LockStateEnum("NORMAL", NORMAL_VALUE);

    /**
     * construct function
     * @param integer lockStateEnum
     */
    private LockStateEnum(String name, int lockStateEnum)
    {
        super(name, lockStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static LockStateEnum getEnum(String lockStateEnum)
    {
        return (LockStateEnum)getEnum(LockStateEnum.class, lockStateEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static LockStateEnum getEnum(int lockStateEnum)
    {
        return (LockStateEnum)getEnum(LockStateEnum.class, lockStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(LockStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(LockStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(LockStateEnum.class);
    }
}