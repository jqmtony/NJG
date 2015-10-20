/**
 * output package name
 */
package com.kingdee.eas.fdc.contract;

import com.kingdee.eas.common.EASBizException;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * output class name
 */
public class ContractException extends EASBizException
{
    private static final String MAINCODE = "10";

    public static final NumericExceptionSubItem NAME_DUP = new NumericExceptionSubItem("000", "NAME_DUP");
    public static final NumericExceptionSubItem PROJ_NULL = new NumericExceptionSubItem("001", "PROJ_NULL");
    public static final NumericExceptionSubItem NUMBER_DUP = new NumericExceptionSubItem("002", "NUMBER_DUP");
    public static final NumericExceptionSubItem FINALSETTLE_DUP = new NumericExceptionSubItem("003", "FINALSETTLE_DUP");
    public static final NumericExceptionSubItem COSTSPLIT_DUP = new NumericExceptionSubItem("004", "COSTSPLIT_DUP");
    public static final NumericExceptionSubItem NOADMINORG = new NumericExceptionSubItem("005", "NoAdminOrg");
    public static final NumericExceptionSubItem PAYREQUESTBILL_NUM_DUP = new NumericExceptionSubItem("006", "PayRequestBill_Num_DUP");
    public static final NumericExceptionSubItem HASPAYMENTBILL = new NumericExceptionSubItem("007", "HasPaymentBill");
    public static final NumericExceptionSubItem HASAUDIT = new NumericExceptionSubItem("008", "HasAudit");
    public static final NumericExceptionSubItem CONTRACTBILLREVISEAMOUNT = new NumericExceptionSubItem("009", "ContractBillReviseAmount");
    public static final NumericExceptionSubItem CANTUNAUDITSETTER = new NumericExceptionSubItem("010", "CANTUNAUDITSETTER");
    public static final NumericExceptionSubItem CANTXIUDING = new NumericExceptionSubItem("011", "CANTXIUDING");
    public static final NumericExceptionSubItem CNTPERIODBEFORE = new NumericExceptionSubItem("012", "CNTPERIODBEFORE");
    public static final NumericExceptionSubItem CLOPRO_HASEND = new NumericExceptionSubItem("013", "CLOPRO_HASEND");
    public static final NumericExceptionSubItem CLOPRO_UNINIT = new NumericExceptionSubItem("014", "CLOPRO_UNINIT");
    public static final NumericExceptionSubItem CLOPRO_HASMONTH = new NumericExceptionSubItem("015", "CLOPRO_HASMONTH");
    public static final NumericExceptionSubItem CLOPRO_HASCONT = new NumericExceptionSubItem("016", "CLOPRO_HASCONT");
    public static final NumericExceptionSubItem CLOPRO_BEFORES = new NumericExceptionSubItem("017", "CLOPRO_BEFORES");
    public static final NumericExceptionSubItem CLOPRO_NOTEND = new NumericExceptionSubItem("018", "CLOPRO_NOTEND");
    public static final NumericExceptionSubItem AUD_AFTERPERIOD = new NumericExceptionSubItem("019", "AUD_AFTERPERIOD");
    public static final NumericExceptionSubItem AUD_FINNOTCLOSE = new NumericExceptionSubItem("020", "AUD_FINNOTCLOSE");
    public static final NumericExceptionSubItem CNTPERIODBEFORECON = new NumericExceptionSubItem("021", "CNTPERIODBEFORECON");
    public static final NumericExceptionSubItem CNTPERIODBEFORECONNOT = new NumericExceptionSubItem("022", "CNTPERIODBEFORECONNOT");
    public static final NumericExceptionSubItem NOSPLITFORAUDIT = new NumericExceptionSubItem("023", "NOSPLITFORAUDIT");
    public static final NumericExceptionSubItem SPLITBEFOREAUDIT = new NumericExceptionSubItem("024", "SPLITBEFOREAUDIT");
    public static final NumericExceptionSubItem HASSPLIT = new NumericExceptionSubItem("025", "HASSPLIT");
    public static final NumericExceptionSubItem SETTLESPLIEDCANNTUNAUDIT = new NumericExceptionSubItem("026", "SETTLESPLIEDCANNTUnAudit");
    public static final NumericExceptionSubItem AUDITFIRST = new NumericExceptionSubItem("027", "AUDITFIRST");
    public static final NumericExceptionSubItem HASACHIVED = new NumericExceptionSubItem("028", "HASACHIVED");
    public static final NumericExceptionSubItem HASSETTLE = new NumericExceptionSubItem("029", "HASSETTLE");
    public static final NumericExceptionSubItem HASSETTLEPAYREQUESTBILL = new NumericExceptionSubItem("030", "HASSETTLEPAYREQUESTBILL");
    public static final NumericExceptionSubItem HASALLAPLIT = new NumericExceptionSubItem("031", "HASALLAPLIT");
    public static final NumericExceptionSubItem WITHMSG = new NumericExceptionSubItem("032", "WITHMSG");
    public static final NumericExceptionSubItem BEFOREBGBAL = new NumericExceptionSubItem("033", "BEFOREBGBAL");
    public static final NumericExceptionSubItem PAYMENTNOSPLIT = new NumericExceptionSubItem("034", "PAYMENTNOSPLIT");
    public static final NumericExceptionSubItem PAYMENTNOTPAY = new NumericExceptionSubItem("035", "PAYMENTNOTPAY");
    public static final NumericExceptionSubItem NOCODEINGRULE = new NumericExceptionSubItem("036", "NOCODEINGRULE");
    public static final NumericExceptionSubItem HASVOUCHER = new NumericExceptionSubItem("037", "HASVOUCHER");
    public static final NumericExceptionSubItem CANNOTCREATVOUCHERFORPAYMENT = new NumericExceptionSubItem("038", "CANNOTCREATVOUCHERFORPAYMENT");
    public static final NumericExceptionSubItem CANNOTFINDCOSTACCOUNTWITHACCOUNT = new NumericExceptionSubItem("039", "CANNOTFINDCOSTACCOUNTWITHACCOUNT");
    public static final NumericExceptionSubItem CANNOTFINDCOSTACCOUNT = new NumericExceptionSubItem("040", "CANNOTFINDCOSTACCOUNT");
    public static final NumericExceptionSubItem HASNOTACOSTACCOUNTWITHACCOUNT = new NumericExceptionSubItem("041", "HASNOTACOSTACCOUNTWITHACCOUNT");
    public static final NumericExceptionSubItem NOTUSEADJUSTMODE = new NumericExceptionSubItem("042", "NOTUSEADJUSTMODE");
    public static final NumericExceptionSubItem CANNOTFINDBEFOREACCOUNTVIEW = new NumericExceptionSubItem("043", "CANNOTFINDBEFOREACCOUNTVIEW");
    public static final NumericExceptionSubItem NOTOPENFINACIAL = new NumericExceptionSubItem("044", "NOTOPENFINACIAL");
    public static final NumericExceptionSubItem MORETHANCOMPLETEPRJAMT = new NumericExceptionSubItem("045", "MORETHANCOMPLETEPRJAMT");
    public static final NumericExceptionSubItem CHANGEBILLNOTAUDIT = new NumericExceptionSubItem("046", "CHANGEBILLNOTAUDIT");
    public static final NumericExceptionSubItem MAINCONHASSETTLED = new NumericExceptionSubItem("047", "MAINCONHASSETTLED");
    public static final NumericExceptionSubItem NOEDITOR = new NumericExceptionSubItem("048", "NOEDITOR");
    public static final NumericExceptionSubItem CANNOTCREATVOUCHERFORPAYMENT2 = new NumericExceptionSubItem("049", "CANNOTCREATVOUCHERFORPAYMENT2");
    public static final NumericExceptionSubItem HASCHANGEAUDITBILL = new NumericExceptionSubItem("050", "HASCHANGEAUDITBILL");
    public static final NumericExceptionSubItem HASCONTRACTCHANGEBILL = new NumericExceptionSubItem("051", "HASCONTRACTCHANGEBILL");
    public static final NumericExceptionSubItem EXISTMODELVERSION = new NumericExceptionSubItem("052", "EXISTMODELVERSION");
    public static final NumericExceptionSubItem HASWORKLOADCONFIRMBILL = new NumericExceptionSubItem("053", "HASWORKLOADCONFIRMBILL");
    /**
     * construct function
     * @param NumericExceptionSubItem info
     * @param Throwable cause
     * @param Object[] params
     */
    public ContractException(NumericExceptionSubItem info, Throwable cause, Object[] params)
    {
        super(info, cause, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Object[] params
     */
    public ContractException(NumericExceptionSubItem info, Object[] params)
    {
        this(info, null, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Throwable cause
     */
    public ContractException(NumericExceptionSubItem info, Throwable cause)
    {
        this(info, cause, null);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info
     */
    public ContractException(NumericExceptionSubItem info)
    {
        this(info, null, null);
    }

    /**
     * getMainCode function
     */
    public String getMainCode()
    {
        return MAINCODE;
    }
}