/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.app;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class ChangeReasonTypeEnum extends StringEnum
{
    public static final String DEISIGN_VALUE = "1";
    public static final String CONSTRUCTION_VALUE = "2";
    public static final String MARKETING_VALUE = "3";
    public static final String OTHER_VALUE = "10";

    public static final ChangeReasonTypeEnum deisign = new ChangeReasonTypeEnum("deisign", DEISIGN_VALUE);
    public static final ChangeReasonTypeEnum construction = new ChangeReasonTypeEnum("construction", CONSTRUCTION_VALUE);
    public static final ChangeReasonTypeEnum marketing = new ChangeReasonTypeEnum("marketing", MARKETING_VALUE);
    public static final ChangeReasonTypeEnum other = new ChangeReasonTypeEnum("other", OTHER_VALUE);

    /**
     * construct function
     * @param String changeReasonTypeEnum
     */
    private ChangeReasonTypeEnum(String name, String changeReasonTypeEnum)
    {
        super(name, changeReasonTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ChangeReasonTypeEnum getEnum(String changeReasonTypeEnum)
    {
        return (ChangeReasonTypeEnum)getEnum(ChangeReasonTypeEnum.class, changeReasonTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ChangeReasonTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ChangeReasonTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ChangeReasonTypeEnum.class);
    }
}