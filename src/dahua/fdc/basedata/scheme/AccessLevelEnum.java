/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.scheme;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class AccessLevelEnum extends StringEnum
{
    public static final String PUBLIC_VALUE = "public";//alias=公共的
    public static final String PROTECTED_VALUE = "protected";//alias=受保护的
    public static final String PACKAGE_VALUE = "package";//alias=包内的
    public static final String PRIVATE_VALUE = "private";//alias=私有的

    public static final AccessLevelEnum PUBLIC = new AccessLevelEnum("PUBLIC", PUBLIC_VALUE);
    public static final AccessLevelEnum PROTECTED = new AccessLevelEnum("PROTECTED", PROTECTED_VALUE);
    public static final AccessLevelEnum PACKAGE = new AccessLevelEnum("PACKAGE", PACKAGE_VALUE);
    public static final AccessLevelEnum PRIVATE = new AccessLevelEnum("PRIVATE", PRIVATE_VALUE);

    /**
     * construct function
     * @param String accessLevelEnum
     */
    private AccessLevelEnum(String name, String accessLevelEnum)
    {
        super(name, accessLevelEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static AccessLevelEnum getEnum(String accessLevelEnum)
    {
        return (AccessLevelEnum)getEnum(AccessLevelEnum.class, accessLevelEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(AccessLevelEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(AccessLevelEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(AccessLevelEnum.class);
    }
}