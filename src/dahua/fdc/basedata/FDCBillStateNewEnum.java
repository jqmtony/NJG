/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class FDCBillStateNewEnum extends StringEnum
{
    public static final String SAVED_VALUE = "1SAVED";
    public static final String SUBMITTED_VALUE = "2SUBMITTED";
    public static final String AUDITTING_VALUE = "3AUDITTING";
    public static final String AUDITTED_VALUE = "4AUDITTED";
    public static final String PUBLISH_VALUE = "10PUBLISH";
    public static final String BACK_VALUE = "11BACK";
    public static final String REVISING_VALUE = "0REVISING";
    public static final String REVISE_VALUE = "12REVISE";

    public static final FDCBillStateNewEnum SAVED = new FDCBillStateNewEnum("SAVED", SAVED_VALUE);
    public static final FDCBillStateNewEnum SUBMITTED = new FDCBillStateNewEnum("SUBMITTED", SUBMITTED_VALUE);
    public static final FDCBillStateNewEnum AUDITTING = new FDCBillStateNewEnum("AUDITTING", AUDITTING_VALUE);
    public static final FDCBillStateNewEnum AUDITTED = new FDCBillStateNewEnum("AUDITTED", AUDITTED_VALUE);
    public static final FDCBillStateNewEnum PUBLISH = new FDCBillStateNewEnum("PUBLISH", PUBLISH_VALUE);
    public static final FDCBillStateNewEnum BACK = new FDCBillStateNewEnum("BACK", BACK_VALUE);
    public static final FDCBillStateNewEnum REVISING = new FDCBillStateNewEnum("REVISING", REVISING_VALUE);
    public static final FDCBillStateNewEnum REVISE = new FDCBillStateNewEnum("REVISE", REVISE_VALUE);

    /**
     * construct function
     * @param String fDCBillStateNewEnum
     */
    private FDCBillStateNewEnum(String name, String fDCBillStateNewEnum)
    {
        super(name, fDCBillStateNewEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static FDCBillStateNewEnum getEnum(String fDCBillStateNewEnum)
    {
        return (FDCBillStateNewEnum)getEnum(FDCBillStateNewEnum.class, fDCBillStateNewEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(FDCBillStateNewEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(FDCBillStateNewEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(FDCBillStateNewEnum.class);
    }
}