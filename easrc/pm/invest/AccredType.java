/**
 * output package name
 */
package com.kingdee.eas.port.pm.invest;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class AccredType extends StringEnum
{
    public static final String TRIAL_VALUE = "1";//alias=≥ı…Û±Ì
    public static final String ACCRED_VALUE = "2";//alias=∆¿…Û±Ì

    public static final AccredType trial = new AccredType("trial", TRIAL_VALUE);
    public static final AccredType accred = new AccredType("accred", ACCRED_VALUE);

    /**
     * construct function
     * @param String accredType
     */
    private AccredType(String name, String accredType)
    {
        super(name, accredType);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static AccredType getEnum(String accredType)
    {
        return (AccredType)getEnum(AccredType.class, accredType);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(AccredType.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(AccredType.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(AccredType.class);
    }
}