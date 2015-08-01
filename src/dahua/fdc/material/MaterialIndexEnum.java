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
public class MaterialIndexEnum extends StringEnum
{
    public static final String BASEINDEX_VALUE = "BASEINDEX";
    public static final String TRAITINDEX_VALUE = "TRAITINDEX";
    public static final String SUPERINDEX_VALUE = "SUPERINDEX";

    public static final MaterialIndexEnum BASEINDEX = new MaterialIndexEnum("BASEINDEX", BASEINDEX_VALUE);
    public static final MaterialIndexEnum TRAITINDEX = new MaterialIndexEnum("TRAITINDEX", TRAITINDEX_VALUE);
    public static final MaterialIndexEnum SUPERINDEX = new MaterialIndexEnum("SUPERINDEX", SUPERINDEX_VALUE);

    /**
     * construct function
     * @param String materialIndexEnum
     */
    private MaterialIndexEnum(String name, String materialIndexEnum)
    {
        super(name, materialIndexEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static MaterialIndexEnum getEnum(String materialIndexEnum)
    {
        return (MaterialIndexEnum)getEnum(MaterialIndexEnum.class, materialIndexEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(MaterialIndexEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(MaterialIndexEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(MaterialIndexEnum.class);
    }
}