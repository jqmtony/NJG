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
public class ConChangeBillSettAfterSignEnum extends StringEnum
{
    public static final String UNAUDITAFTERSIGN_VALUE = "1UnAuditAfterSign";
    public static final String AUDITTINGAFTERSIGN_VALUE = "2AudittingAfterSign";
    public static final String AUDITEDAFTERSIGN_VALUE = "3AuditedAfterSign";

    public static final ConChangeBillSettAfterSignEnum UnAuditAfterSign = new ConChangeBillSettAfterSignEnum("UnAuditAfterSign", UNAUDITAFTERSIGN_VALUE);
    public static final ConChangeBillSettAfterSignEnum AudittingAfterSign = new ConChangeBillSettAfterSignEnum("AudittingAfterSign", AUDITTINGAFTERSIGN_VALUE);
    public static final ConChangeBillSettAfterSignEnum AuditedAfterSign = new ConChangeBillSettAfterSignEnum("AuditedAfterSign", AUDITEDAFTERSIGN_VALUE);

    /**
     * construct function
     * @param String conChangeBillSettAfterSignEnum
     */
    private ConChangeBillSettAfterSignEnum(String name, String conChangeBillSettAfterSignEnum)
    {
        super(name, conChangeBillSettAfterSignEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ConChangeBillSettAfterSignEnum getEnum(String conChangeBillSettAfterSignEnum)
    {
        return (ConChangeBillSettAfterSignEnum)getEnum(ConChangeBillSettAfterSignEnum.class, conChangeBillSettAfterSignEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ConChangeBillSettAfterSignEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ConChangeBillSettAfterSignEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ConChangeBillSettAfterSignEnum.class);
    }
}