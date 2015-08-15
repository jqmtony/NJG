/**
 * output package name
 */
package com.kingdee.eas.fdc.contract;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class OfferEnum extends StringEnum
{
    public static final String SELFCOM_VALUE = "SELFCOM";//alias=我司
    public static final String DESIGNCOM_VALUE = "DESIGNCOM";//alias=设计公司
    public static final String CONSTRPARTY_VALUE = "CONSTRPARTY";//alias=施工方
    public static final String SUPERVISER_VALUE = "SUPERVISER";//alias=监理方

    public static final OfferEnum SELFCOM = new OfferEnum("SELFCOM", SELFCOM_VALUE);
    public static final OfferEnum DESIGNCOM = new OfferEnum("DESIGNCOM", DESIGNCOM_VALUE);
    public static final OfferEnum CONSTRPARTY = new OfferEnum("CONSTRPARTY", CONSTRPARTY_VALUE);
    public static final OfferEnum SUPERVISER = new OfferEnum("SUPERVISER", SUPERVISER_VALUE);

    /**
     * construct function
     * @param String offerEnum
     */
    private OfferEnum(String name, String offerEnum)
    {
        super(name, offerEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static OfferEnum getEnum(String offerEnum)
    {
        return (OfferEnum)getEnum(OfferEnum.class, offerEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(OfferEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(OfferEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(OfferEnum.class);
    }
}