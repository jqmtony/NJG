/**
 * output package name
 */
package com.kingdee.eas.custom.richbase;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class CardType extends StringEnum
{
    public static final String BLACK_VALUE = "5";//alias=
    public static final String YCX_VALUE = "10";//alias=一次性消费卡
    public static final String CZK_VALUE = "20";//alias=充值卡

    public static final CardType black = new CardType("black", BLACK_VALUE);
    public static final CardType ycx = new CardType("ycx", YCX_VALUE);
    public static final CardType czk = new CardType("czk", CZK_VALUE);

    /**
     * construct function
     * @param String cardType
     */
    private CardType(String name, String cardType)
    {
        super(name, cardType);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static CardType getEnum(String cardType)
    {
        return (CardType)getEnum(CardType.class, cardType);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(CardType.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(CardType.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(CardType.class);
    }
}