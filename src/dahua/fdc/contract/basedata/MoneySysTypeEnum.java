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
public class MoneySysTypeEnum extends StringEnum
{	
    public static final String SALEHOUSESYS_VALUE = "SalehouseSys";
    public static final String TENANCYSYS_VALUE = "TenancySys";
    public static final String MANAGESYS_VALUE = "ManageSys";

    public static final MoneySysTypeEnum SalehouseSys = new MoneySysTypeEnum("SalehouseSys", SALEHOUSESYS_VALUE);
    public static final MoneySysTypeEnum TenancySys = new MoneySysTypeEnum("TenancySys", TENANCYSYS_VALUE);
    public static final MoneySysTypeEnum ManageSys = new MoneySysTypeEnum("ManageSys", MANAGESYS_VALUE);

    /**
     * construct function
     * @param String moneySysTypeEnum
     */
    private MoneySysTypeEnum(String name, String moneySysTypeEnum)
    {
        super(name, moneySysTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static MoneySysTypeEnum getEnum(String moneySysTypeEnum)
    {
        return (MoneySysTypeEnum)getEnum(MoneySysTypeEnum.class, moneySysTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(MoneySysTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(MoneySysTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(MoneySysTypeEnum.class);
    }
}