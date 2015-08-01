/**
 * output package name
 */
package com.kingdee.eas.fdc.finance;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class SignStateEnum extends StringEnum
{
    public static final String SIGNED_VALUE = "10";
    public static final String UNSIGN_VALUE = "20";

    public static final SignStateEnum SIGNED = new SignStateEnum("SIGNED", SIGNED_VALUE);
    public static final SignStateEnum UNSIGN = new SignStateEnum("UNSIGN", UNSIGN_VALUE);

    /**
     * construct function
     * @param String signStateEnum
     */
    private SignStateEnum(String name, String signStateEnum)
    {
        super(name, signStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SignStateEnum getEnum(String signStateEnum)
    {
        return (SignStateEnum)getEnum(SignStateEnum.class, signStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SignStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SignStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SignStateEnum.class);
    }
}