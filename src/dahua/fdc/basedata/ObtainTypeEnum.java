/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.IntEnum;

/**
 * output class name
 */
public class ObtainTypeEnum extends IntEnum
{
    public static final int INVITE_VALUE = 10;//alias=ÕÐ
    public static final int AUCTION_VALUE = 20;//alias=ÅÄ
    public static final int HANG_VALUE = 30;//alias=¹Ò
    public static final int CANOPERATOR_VALUE = 40;//alias=¿É²Ù×÷

    public static final ObtainTypeEnum INVITE = new ObtainTypeEnum("INVITE", INVITE_VALUE);
    public static final ObtainTypeEnum AUCTION = new ObtainTypeEnum("AUCTION", AUCTION_VALUE);
    public static final ObtainTypeEnum HANG = new ObtainTypeEnum("HANG", HANG_VALUE);
    public static final ObtainTypeEnum CANOPERATOR = new ObtainTypeEnum("CANOPERATOR", CANOPERATOR_VALUE);

    /**
     * construct function
     * @param integer obtainTypeEnum
     */
    private ObtainTypeEnum(String name, int obtainTypeEnum)
    {
        super(name, obtainTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ObtainTypeEnum getEnum(String obtainTypeEnum)
    {
        return (ObtainTypeEnum)getEnum(ObtainTypeEnum.class, obtainTypeEnum);
    }

    /**
     * getEnum function
     * @param String arguments
     */
    public static ObtainTypeEnum getEnum(int obtainTypeEnum)
    {
        return (ObtainTypeEnum)getEnum(ObtainTypeEnum.class, obtainTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ObtainTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ObtainTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ObtainTypeEnum.class);
    }
}