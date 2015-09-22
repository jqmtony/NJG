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
public class ChangeAuditBillType extends StringEnum
{
    public static final String CHANGEAUDITREQUEST_VALUE = "10";//alias=设计变更申请单
    public static final String DESIGNCHANGEAUDIT_VALUE = "20";//alias=设计变更审批表
    public static final String PROJECTCHANGEAUDIT_VALUE = "30";//alias=工程指令单
    public static final String TECHCHANGEAUDIT_VALUE = "40";//alias=技术核定审批表
    public static final String TECHECONCHANGEAUDIT_VALUE = "50";//alias=技术经济签证单

    public static final ChangeAuditBillType ChangeAuditRequest = new ChangeAuditBillType("ChangeAuditRequest", CHANGEAUDITREQUEST_VALUE);
    public static final ChangeAuditBillType DesignChangeAudit = new ChangeAuditBillType("DesignChangeAudit", DESIGNCHANGEAUDIT_VALUE);
    public static final ChangeAuditBillType ProjectChangeAudit = new ChangeAuditBillType("ProjectChangeAudit", PROJECTCHANGEAUDIT_VALUE);
    public static final ChangeAuditBillType TechChangeAudit = new ChangeAuditBillType("TechChangeAudit", TECHCHANGEAUDIT_VALUE);
    public static final ChangeAuditBillType TechEconChangeAudit = new ChangeAuditBillType("TechEconChangeAudit", TECHECONCHANGEAUDIT_VALUE);

    /**
     * construct function
     * @param String changeAuditBillType
     */
    private ChangeAuditBillType(String name, String changeAuditBillType)
    {
        super(name, changeAuditBillType); 
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ChangeAuditBillType getEnum(String changeAuditBillType)
    {
        return (ChangeAuditBillType)getEnum(ChangeAuditBillType.class, changeAuditBillType);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ChangeAuditBillType.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ChangeAuditBillType.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ChangeAuditBillType.class);
    }
}