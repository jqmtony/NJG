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
public class BillAuditDepartEnum extends StringEnum
{
    public static final String HANDLER_VALUE = "00";
    public static final String HANDLERDEPART_VALUE = "01";
    public static final String PROJECTCOSTMANAGEDEPART_VALUE = "02";
    public static final String RELATEDEPART_VALUE = "03";
    public static final String LAWYER_VALUE = "04";
    public static final String PROJECTPRINCIPAL_VALUE = "05";
    public static final String FINANCPRINCIPAL_VALUE = "06";
    public static final String MANAGER_VALUE = "07";
    public static final String OPERATIONDEPART_VALUE = "08";
    public static final String DESIGNDEPART_VALUE = "09";
    public static final String DEVELOPDEPART_VALUE = "10";
    public static final String COMPANYRELATEDEPART_VALUE = "11";
    public static final String COMPANYFINANCEDEPART_VALUE = "12";
    public static final String PRESIDENT_VALUE = "13";
    public static final String FINANCEDEPART_VALUE = "14";

    public static final BillAuditDepartEnum handler = new BillAuditDepartEnum("handler", HANDLER_VALUE);
    public static final BillAuditDepartEnum handlerDepart = new BillAuditDepartEnum("handlerDepart", HANDLERDEPART_VALUE);
    public static final BillAuditDepartEnum projectCostManageDepart = new BillAuditDepartEnum("projectCostManageDepart", PROJECTCOSTMANAGEDEPART_VALUE);
    public static final BillAuditDepartEnum relateDepart = new BillAuditDepartEnum("relateDepart", RELATEDEPART_VALUE);
    public static final BillAuditDepartEnum lawyer = new BillAuditDepartEnum("lawyer", LAWYER_VALUE);
    public static final BillAuditDepartEnum projectPrincipal = new BillAuditDepartEnum("projectPrincipal", PROJECTPRINCIPAL_VALUE);
    public static final BillAuditDepartEnum financPrincipal = new BillAuditDepartEnum("financPrincipal", FINANCPRINCIPAL_VALUE);
    public static final BillAuditDepartEnum manager = new BillAuditDepartEnum("manager", MANAGER_VALUE);
    public static final BillAuditDepartEnum operationDepart = new BillAuditDepartEnum("operationDepart", OPERATIONDEPART_VALUE);
    public static final BillAuditDepartEnum designDepart = new BillAuditDepartEnum("designDepart", DESIGNDEPART_VALUE);
    public static final BillAuditDepartEnum developDepart = new BillAuditDepartEnum("developDepart", DEVELOPDEPART_VALUE);
    public static final BillAuditDepartEnum companyRelateDepart = new BillAuditDepartEnum("companyRelateDepart", COMPANYRELATEDEPART_VALUE);
    public static final BillAuditDepartEnum companyFinanceDepart = new BillAuditDepartEnum("companyFinanceDepart", COMPANYFINANCEDEPART_VALUE);
    public static final BillAuditDepartEnum president = new BillAuditDepartEnum("president", PRESIDENT_VALUE);
    public static final BillAuditDepartEnum financeDepart = new BillAuditDepartEnum("financeDepart", FINANCEDEPART_VALUE);

    /**
     * construct function
     * @param String billAuditDepartEnum
     */
    private BillAuditDepartEnum(String name, String billAuditDepartEnum)
    {
        super(name, billAuditDepartEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BillAuditDepartEnum getEnum(String billAuditDepartEnum)
    {
        return (BillAuditDepartEnum)getEnum(BillAuditDepartEnum.class, billAuditDepartEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BillAuditDepartEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BillAuditDepartEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BillAuditDepartEnum.class);
    }
}