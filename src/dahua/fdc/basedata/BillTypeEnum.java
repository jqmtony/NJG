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
public class BillTypeEnum extends StringEnum
{
    public static final String CONTRACTBILL_VALUE = "1";
    public static final String CONTRACTREVISE_VALUE = "2";
    public static final String CHANGEAUDIT_VALUE = "3";
    public static final String CONTRACTCHANGE_VALUE = "4";
    public static final String CONTRACTSETTLE_VALUE = "5";
    public static final String CONTRACTWITHOUTTEXT_VALUE = "6";
    public static final String COMPENSATION_VALUE = "7";
    public static final String GUERDONBILL_VALUE = "8";
    public static final String DEDUCTBILL_VALUE = "9";
    public static final String PAYREQUESTBILL_VALUE = "10";
    public static final String PAYMENTBILL_VALUE = "11";
    public static final String RECEIVEMENTBILL_VALUE = "12";

    public static final BillTypeEnum ContractBill = new BillTypeEnum("ContractBill", CONTRACTBILL_VALUE);
    public static final BillTypeEnum ContractRevise = new BillTypeEnum("ContractRevise", CONTRACTREVISE_VALUE);
    public static final BillTypeEnum ChangeAudit = new BillTypeEnum("ChangeAudit", CHANGEAUDIT_VALUE);
    public static final BillTypeEnum ContractChange = new BillTypeEnum("ContractChange", CONTRACTCHANGE_VALUE);
    public static final BillTypeEnum ContractSettle = new BillTypeEnum("ContractSettle", CONTRACTSETTLE_VALUE);
    public static final BillTypeEnum ContractWithOutText = new BillTypeEnum("ContractWithOutText", CONTRACTWITHOUTTEXT_VALUE);
    public static final BillTypeEnum Compensation = new BillTypeEnum("Compensation", COMPENSATION_VALUE);
    public static final BillTypeEnum GuerdonBill = new BillTypeEnum("GuerdonBill", GUERDONBILL_VALUE);
    public static final BillTypeEnum DeductBill = new BillTypeEnum("DeductBill", DEDUCTBILL_VALUE);
    public static final BillTypeEnum PayRequestBill = new BillTypeEnum("PayRequestBill", PAYREQUESTBILL_VALUE);
    public static final BillTypeEnum PaymentBill = new BillTypeEnum("PaymentBill", PAYMENTBILL_VALUE);
    public static final BillTypeEnum ReceivementBill = new BillTypeEnum("ReceivementBill", RECEIVEMENTBILL_VALUE);

    /**
     * construct function
     * @param String billTypeEnum
     */
    private BillTypeEnum(String name, String billTypeEnum)
    {
        super(name, billTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BillTypeEnum getEnum(String billTypeEnum)
    {
        return (BillTypeEnum)getEnum(BillTypeEnum.class, billTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BillTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BillTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BillTypeEnum.class);
    }
}