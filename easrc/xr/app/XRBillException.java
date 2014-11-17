/**
 * output package name
 */
package com.kingdee.eas.xr.app;

import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.eas.common.EASBizException;

/**
 * output class name
 */
public class XRBillException extends EASBizException
{
    private static final String MAINCODE = "01";

    public static final NumericExceptionSubItem PARTOFDELETEDBILL_CANNOT_BE_DELETE = new NumericExceptionSubItem("001", "PARTOFDELETEDBILL_CANNOT_BE_DELETE");
    public static final NumericExceptionSubItem SALEPERSONISNOTEXIST = new NumericExceptionSubItem("000", "SalePersonIsNotExist");
    public static final NumericExceptionSubItem PURPERSONISNOTEXIST = new NumericExceptionSubItem("002", "PurPersonIsNotExist");
    public static final NumericExceptionSubItem CHECKNOTEXIST = new NumericExceptionSubItem("003", "CheckNotExist");
    public static final NumericExceptionSubItem CHECKSUBMITOK = new NumericExceptionSubItem("004", "CheckSubmitOK");
    public static final NumericExceptionSubItem CHECKSUBMITNOTOK = new NumericExceptionSubItem("005", "CheckSubmitNotOK");
    public static final NumericExceptionSubItem CHECKAUDITEDOK = new NumericExceptionSubItem("006", "CheckAuditedOK");
    public static final NumericExceptionSubItem CHECKAUDITEDNOTOK = new NumericExceptionSubItem("007", "CheckAuditedNotOK");
    public static final NumericExceptionSubItem CHECKBLOCKEDOK = new NumericExceptionSubItem("008", "CheckBlockedOK");
    public static final NumericExceptionSubItem CHECKBLOCKEDNOTOK = new NumericExceptionSubItem("009", "CheckBlockedNotOK");
    public static final NumericExceptionSubItem CHECKDELETEDOK = new NumericExceptionSubItem("010", "CheckDeletedOK");
    public static final NumericExceptionSubItem CHECKDELETEDNOTOK = new NumericExceptionSubItem("011", "CheckDeletedNotOK");
    public static final NumericExceptionSubItem CHECKCLOSEDOK = new NumericExceptionSubItem("012", "CheckClosedOK");
    public static final NumericExceptionSubItem CHECKCLOSEDNOTOK = new NumericExceptionSubItem("013", "CheckClosedNotOK");
    public static final NumericExceptionSubItem CHECKISVOUCHEREDOK = new NumericExceptionSubItem("014", "CheckIsVoucheredOK");
    public static final NumericExceptionSubItem CHECKISVOUCHEREDNOTOK = new NumericExceptionSubItem("015", "CheckIsVoucheredNotOK");
    public static final NumericExceptionSubItem BIZDATEISNULL = new NumericExceptionSubItem("016", "BizDateIsNull");
    public static final NumericExceptionSubItem NUMBERRULEERROR = new NumericExceptionSubItem("017", "NumberRuleError");
    public static final NumericExceptionSubItem BIZDATEBEFOREPERIOD = new NumericExceptionSubItem("018", "BizDateBeforePeriod");
    public static final NumericExceptionSubItem BIZDATEAFTERPERIOD = new NumericExceptionSubItem("026", "BIZDATEAFTERPERIOD");
    public static final NumericExceptionSubItem CURRENTPERIODISNULL = new NumericExceptionSubItem("019", "CurrentPeriodIsNull");
    public static final NumericExceptionSubItem CLOSEACCOUNT_AUDIT = new NumericExceptionSubItem("020", "CLOSEACCOUNT_AUDIT");
    public static final NumericExceptionSubItem CLOSEACCOUNT_DEL = new NumericExceptionSubItem("021", "CLOSEACCOUNT_DEL");
    public static final NumericExceptionSubItem CLOSEACCOUNT_SAVE = new NumericExceptionSubItem("022", "CLOSEACCOUNT_SAVE");
    public static final NumericExceptionSubItem NOCORDRULE = new NumericExceptionSubItem("023", "NoCordRule");
    public static final NumericExceptionSubItem VURCHERFIISNULL = new NumericExceptionSubItem("024", "VURCHERFIISNULL");
    public static final NumericExceptionSubItem GENERATEVOUCHER_CONFLICT = new NumericExceptionSubItem("025", "GENERATEVOUCHER_CONFLICT");
    public static final NumericExceptionSubItem WRITEBACK_OBJECT_CONFICTED = new NumericExceptionSubItem("027", "WRITEBACK_OBJECT_CONFICTED");
    public static final NumericExceptionSubItem GETBIZPERIODFAIL = new NumericExceptionSubItem("028", "GETBIZPERIODFAIL");
    public static final NumericExceptionSubItem NULL_EXCHANGERATE = new NumericExceptionSubItem("029", "NULL_EXCHANGERATE");
    public static final NumericExceptionSubItem MATERIALFINOTAPPROVE = new NumericExceptionSubItem("030", "MATERIALFINOTAPPROVE");
    public static final NumericExceptionSubItem NOMAINBIZORG = new NumericExceptionSubItem("031", "NOMAINBIZORG");
    public static final NumericExceptionSubItem ORGTYPEERROE = new NumericExceptionSubItem("032", "ORGTYPEERROE");
    public static final NumericExceptionSubItem NOMAINBIZORGONSAVE = new NumericExceptionSubItem("033", "NOMAINBIZORGONSAVE");
    public static final NumericExceptionSubItem WRITE_BACE_ERROR = new NumericExceptionSubItem("034", "WRITE_BACE_ERROR");
    public static final NumericExceptionSubItem HASDESTBILL_CANNOTUNAUDIT = new NumericExceptionSubItem("035", "HASDESTBILL_CANNOTUNAUDIT");
    public static final NumericExceptionSubItem HASALTER_CANNOTUNAUDIT = new NumericExceptionSubItem("036", "HASALTER_CANNOTUNAUDIT");
    public static final NumericExceptionSubItem HASBLOCKED_CANNOTUNAUDIT = new NumericExceptionSubItem("037", "HASBLOCKED_CANNOTUNAUDIT");
    public static final NumericExceptionSubItem HASENTRYLOCKED_CANNOTUNAUDIT = new NumericExceptionSubItem("038", "HASENTRYLOCKED_CANNOTUNAUDIT");
    public static final NumericExceptionSubItem PARTCANNOT_UNAUDIT = new NumericExceptionSubItem("039", "PARTCANNOT_UNAUDIT");
    public static final NumericExceptionSubItem ENTRIESISNOTAUDIT_CANNOTUNAUDIT = new NumericExceptionSubItem("040", "ENTRIESISNOTAUDIT_CANNOTUNAUDIT");
    public static final NumericExceptionSubItem CHECKSTATEUNSTATE = new NumericExceptionSubItem("041", "CHECKSTATEUNSTATE");
    public static final NumericExceptionSubItem HASREVERSED_CANNOTUNAUDIT = new NumericExceptionSubItem("042", "HASREVERSED_CANNOTUNAUDIT");
    public static final NumericExceptionSubItem REVERSE_QTY_TOO_BIG = new NumericExceptionSubItem("043", "REVERSE_QTY_TOO_BIG");
    public static final NumericExceptionSubItem REVERSE_ORDER_CANNOT_COPY = new NumericExceptionSubItem("044", "REVERSE_ORDER_CANNOT_COPY");
    public static final NumericExceptionSubItem REVERSE_ORDER_CANNOT_ALTER = new NumericExceptionSubItem("045", "REVERSE_ORDER_CANNOT_ALTER");
    public static final NumericExceptionSubItem REVERSE_ORDER_CANNOT_LOCK = new NumericExceptionSubItem("046", "REVERSE_ORDER_CANNOT_LOCK");
    public static final NumericExceptionSubItem REVERSE_QTY_MUST_BIG_THAN_ZERO = new NumericExceptionSubItem("047", "REVERSE_QTY_MUST_BIG_THAN_ZERO");
    public static final NumericExceptionSubItem REVERSE_ORDER_CANNOT_FREEZE = new NumericExceptionSubItem("048", "REVERSE_ORDER_CANNOT_FREEZE");
    public static final NumericExceptionSubItem REVERSE_ORDER_CANNOT_REVOKE = new NumericExceptionSubItem("049", "REVERSE_ORDER_CANNOT_REVOKE");
    public static final NumericExceptionSubItem REVERSE_ORDER_CANNOT_CLOSE = new NumericExceptionSubItem("050", "REVERSE_ORDER_CANNOT_CLOSE");
    public static final NumericExceptionSubItem ISUNAUDITINPARAM = new NumericExceptionSubItem("051", "ISUNAUDITINPARAM");
    public static final NumericExceptionSubItem CHECK_UNAUDIT = new NumericExceptionSubItem("052", "CHECK_UNAUDIT");
    public static final NumericExceptionSubItem BILLMUSTSUBMIT = new NumericExceptionSubItem("053", "BILLMUSTSUBMIT");
    public static final NumericExceptionSubItem BIZDATENOTNULL = new NumericExceptionSubItem("054", "bizDateNotNull");
    public static final NumericExceptionSubItem ISREVERSED_CANNOTUNAUDIT = new NumericExceptionSubItem("055", "ISREVERSED_CANNOTUNAUDIT");
    public static final NumericExceptionSubItem CHECK_UNAUDIT_PARAM = new NumericExceptionSubItem("056", "CHECK_UNAUDIT_PARAM");
    public static final NumericExceptionSubItem FIXREWRITEQTYERROR = new NumericExceptionSubItem("057", "FIXREWRITEQTYERROR");
    public static final NumericExceptionSubItem CHECKRELEASEDOK = new NumericExceptionSubItem("058", "CheckReleasedOK");
    public static final NumericExceptionSubItem CHECKRELEASEDNOTOK = new NumericExceptionSubItem("059", "CheckReleasedNotOK");
    public static final NumericExceptionSubItem STATUSERROR_CANNOTMULTIAPPROVE = new NumericExceptionSubItem("060", "STATUSERROR_CANNOTMULTIAPPROVE");
    public static final NumericExceptionSubItem CURRENT_PERIOD_COLSED = new NumericExceptionSubItem("061", "CURRENT_PERIOD_COLSED");
    public static final NumericExceptionSubItem BIZDATEAFTERPERIOD_VIRTUAL = new NumericExceptionSubItem("062", "BIZDATEAFTERPERIOD_VIRTUAL");
    public static final NumericExceptionSubItem BIZDATEBEFOREPERIOD_VIRTUAL = new NumericExceptionSubItem("063", "BIZDATEBEFOREPERIOD_VIRTUAL");
    public static final NumericExceptionSubItem BIZDATEISNULL_VIRTUAL = new NumericExceptionSubItem("064", "BIZDATEISNULL_VIRTUAL");
    public static final NumericExceptionSubItem CURRENTPERIODISNULL_VIRTUAL = new NumericExceptionSubItem("065", "CURRENTPERIODISNULL_VIRTUAL");
    public static final NumericExceptionSubItem CUSTOMERPARAMSERROR = new NumericExceptionSubItem("066", "CustomerParamsError");
    public static final NumericExceptionSubItem EX_MSG_FOR_RPC = new NumericExceptionSubItem("067", "EX_MSG_FOR_RPC");
    public static final NumericExceptionSubItem CHECKBILLNUMBLANK = new NumericExceptionSubItem("068", "CHECKBILLNUMBLANK");
    public static final NumericExceptionSubItem ARCLOSED_CANNOTUNAUDIT = new NumericExceptionSubItem("069", "ARCLOSED_CANNOTUNAUDIT");
    public static final NumericExceptionSubItem CANNOT_AUDIT_FICURCARD = new NumericExceptionSubItem("070", "CANNOT_AUDIT_FICURCARD");
    public static final NumericExceptionSubItem CURRENTPERIODISNULLFORINV = new NumericExceptionSubItem("071", "CURRENTPERIODISNULLFORINV");
    public static final NumericExceptionSubItem CURRENT_PERIOD_COLSED_VIRTUAL = new NumericExceptionSubItem("072", "CURRENT_PERIOD_COLSED_VIRTUAL");
    public static final NumericExceptionSubItem MATCHED_ORDER_CANNOT_REVOKE = new NumericExceptionSubItem("073", "MATCHED_ORDER_CANNOT_REVOKE");
    public static final NumericExceptionSubItem SALEORDERENTRYCLOSED_CONNOTAUDIT = new NumericExceptionSubItem("074", "SALEORDERENTRYCLOSED_CONNOTAUDIT");
    public static final NumericExceptionSubItem SALEORDERENTRYCLOSED_CONNOTUNAUDIT = new NumericExceptionSubItem("075", "SALEORDERENTRYCLOSED_CONNOTUNAUDIT");
    public static final NumericExceptionSubItem BIZDATE_REVERSE_NOT_EQUAL_ORIGINAL = new NumericExceptionSubItem("076", "BIZDATE_REVERSE_NOT_EQUAL_ORIGINAL");
    public static final NumericExceptionSubItem ENTRIESNOTNULL = new NumericExceptionSubItem("077", "ENTRIESNOTNULL");
    public static final NumericExceptionSubItem ENTRIESPRICENOTNULL = new NumericExceptionSubItem("078", "ENTRIESPRICENOTNULL");
    public static final NumericExceptionSubItem ENTRIESTAXPRICENOTNULL = new NumericExceptionSubItem("079", "ENTRIESTAXPRICENOTNULL");
    public static final NumericExceptionSubItem ENTRYSNOPRICEANDAMOUNT = new NumericExceptionSubItem("080", "EntrysNoPriceAndAmount");
    public static final NumericExceptionSubItem ENTRYSNOTAXPRICEANDTAXAMOUNT = new NumericExceptionSubItem("081", "EntrysNoTaxPriceAndTaxAmount");
    public static final NumericExceptionSubItem AUDITBILL_CANLOCK = new NumericExceptionSubItem("082", "AUDITBILL_CANLOCK");
    public static final NumericExceptionSubItem BILLTYPE_ISNULL = new NumericExceptionSubItem("083", "BILLTYPE_ISNULL");
    public static final NumericExceptionSubItem EXCEPTION84 = new NumericExceptionSubItem("084", "Exception84");
    public static final NumericExceptionSubItem EXCEPTION85 = new NumericExceptionSubItem("085", "Exception85");
    public static final NumericExceptionSubItem EXCEPTION86 = new NumericExceptionSubItem("086", "Exception86");
    public static final NumericExceptionSubItem EXCEPTION87 = new NumericExceptionSubItem("087", "Exception87");
    public static final NumericExceptionSubItem CHECKSAVENOTOK = new NumericExceptionSubItem("088", "CHECKSAVENOTOK");
    public static final NumericExceptionSubItem CHECKSAVEOK = new NumericExceptionSubItem("089", "CHECKSAVEOK");
    public static final NumericExceptionSubItem NOBUDGET = new NumericExceptionSubItem("090", "∑—”√‘§À„≤ª◊„");

    /**
     * construct function
     * @param NumericExceptionSubItem info
     * @param Throwable cause
     * @param Object[] params
     */
    public XRBillException(NumericExceptionSubItem info, Throwable cause, Object[] params)
    {
        super(info, cause, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Object[] params
     */
    public XRBillException(NumericExceptionSubItem info, Object[] params)
    {
        this(info, null, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Throwable cause
     */
    public XRBillException(NumericExceptionSubItem info, Throwable cause)
    {
        this(info, cause, null);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info
     */
    public XRBillException(NumericExceptionSubItem info)
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