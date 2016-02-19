/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.app;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class changAuditBillSf extends StringEnum
{
    public static final String NONE_VALUE = "3";//alias= 
    public static final String YES_VALUE = "1";//alias=ÊÇ
    public static final String NO_VALUE = "2";//alias=·ñ

    public static final changAuditBillSf none = new changAuditBillSf("none", NONE_VALUE);
    public static final changAuditBillSf yes = new changAuditBillSf("yes", YES_VALUE);
    public static final changAuditBillSf no = new changAuditBillSf("no", NO_VALUE);

    /**
     * construct function
     * @param String changAuditBillSf
     */
    private changAuditBillSf(String name, String changAuditBillSf)
    {
        super(name, changAuditBillSf);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static changAuditBillSf getEnum(String changAuditBillSf)
    {
        return (changAuditBillSf)getEnum(changAuditBillSf.class, changAuditBillSf);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(changAuditBillSf.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(changAuditBillSf.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(changAuditBillSf.class);
    }
}