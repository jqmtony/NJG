/**
 * output package name
 */
package com.kingdee.eas.fdc.contract;

import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.eas.common.EASBizException;

/**
 * output class name
 */
public class PayRequestBillException extends EASBizException
{
    private static final String MAINCODE = "00";

    public static final NumericExceptionSubItem NOTSUPPORTOPRT = new NumericExceptionSubItem("000", "NOTSupportOprt");
    public static final NumericExceptionSubItem SAVEFIRST = new NumericExceptionSubItem("001", "SaveFirst");
    public static final NumericExceptionSubItem CANNT_UNAUDIT = new NumericExceptionSubItem("002", "CANNT_UNAUDIT");
    public static final NumericExceptionSubItem CHECKLSTAMT = new NumericExceptionSubItem("003", "checkLstAmt");
    public static final NumericExceptionSubItem MORETHANQUALITYGUARANTE = new NumericExceptionSubItem("004", "MoreThanQualityGuarante");
    public static final NumericExceptionSubItem CANTSELECTKEEPAMT = new NumericExceptionSubItem("005", "cantSelectKeepAmt");
    public static final NumericExceptionSubItem CANTSELECTPROGRESSAMT = new NumericExceptionSubItem("006", "cantSelectProgressAmt");
    public static final NumericExceptionSubItem MUSTSETTLE = new NumericExceptionSubItem("007", "mustSettle");
    public static final NumericExceptionSubItem WITHOUTCONTRACTEXEC = new NumericExceptionSubItem("008", "WithoutContractExec");
    public static final NumericExceptionSubItem BEFOREBGBAL = new NumericExceptionSubItem("009", "BEFOREBGBAL");
    public static final NumericExceptionSubItem PROCNTUNAUDIT = new NumericExceptionSubItem("010", "PROCNTUNAUDIT");
    public static final NumericExceptionSubItem MUSTCONPAYPLAN = new NumericExceptionSubItem("011", "MUSTCONPAYPLAN");
    public static final NumericExceptionSubItem ISCONTROLPAYMENT = new NumericExceptionSubItem("012", "ISCONTROLPAYMENT");

    /**
     * construct function
     * @param NumericExceptionSubItem info
     * @param Throwable cause
     * @param Object[] params
     */
    public PayRequestBillException(NumericExceptionSubItem info, Throwable cause, Object[] params)
    {
        super(info, cause, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Object[] params
     */
    public PayRequestBillException(NumericExceptionSubItem info, Object[] params)
    {
        this(info, null, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Throwable cause
     */
    public PayRequestBillException(NumericExceptionSubItem info, Throwable cause)
    {
        this(info, cause, null);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info
     */
    public PayRequestBillException(NumericExceptionSubItem info)
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