/**
 * output package name
 */
package com.kingdee.eas.fdc.material;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class MaterialStateEnum extends StringEnum
{
    public static final String VALID_VALUE = "VALID";
    public static final String INVALID_VALUE = "INVALID";

    public static final MaterialStateEnum VALID = new MaterialStateEnum("VALID", VALID_VALUE);
    public static final MaterialStateEnum INVALID = new MaterialStateEnum("INVALID", INVALID_VALUE);

    /**
     * construct function
     * @param String materialStateEnum
     */
    private MaterialStateEnum(String name, String materialStateEnum)
    {
        super(name, materialStateEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static MaterialStateEnum getEnum(String materialStateEnum)
    {
        return (MaterialStateEnum)getEnum(MaterialStateEnum.class, materialStateEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(MaterialStateEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(MaterialStateEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(MaterialStateEnum.class);
    }
}