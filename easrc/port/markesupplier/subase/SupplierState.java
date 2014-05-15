/**
 * output package name
 */
package com.kingdee.eas.port.markesupplier.subase;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class SupplierState extends StringEnum
{
    public static final String SAVE_VALUE = "1";//alias=已保存
    public static final String SUBMIT_VALUE = "2";//alias=已提交
    public static final String AUDIT_VALUE = "3";//alias=已审核

    public static final SupplierState Save = new SupplierState("Save", SAVE_VALUE);
    public static final SupplierState submit = new SupplierState("submit", SUBMIT_VALUE);
    public static final SupplierState audit = new SupplierState("audit", AUDIT_VALUE);

    /**
     * construct function
     * @param String supplierState
     */
    private SupplierState(String name, String supplierState)
    {
        super(name, supplierState);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static SupplierState getEnum(String supplierState)
    {
        return (SupplierState)getEnum(SupplierState.class, supplierState);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(SupplierState.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(SupplierState.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(SupplierState.class);
    }
}