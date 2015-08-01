/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata;

import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.eas.common.EASBizException;

/**
 * output class name
 */
public class FDCException extends EASBizException
{
    private static final String MAINCODE = "10";

    public static final NumericExceptionSubItem TOOMANYRECORDEXCEPTION = new NumericExceptionSubItem("000", "TooManyRecordException");
    public static final NumericExceptionSubItem PRJSTATEWRONG = new NumericExceptionSubItem("001", "PrjStateWrong");
    public static final NumericExceptionSubItem PRJNOCOST = new NumericExceptionSubItem("002", "PrjNoCost");
    public static final NumericExceptionSubItem NOCLOSEFORNOTZERO = new NumericExceptionSubItem("003", "NoCloseForNotZero");
    public static final NumericExceptionSubItem NOCLOSEFORNOTSETT = new NumericExceptionSubItem("004", "NoCloseForNotSett");
    public static final NumericExceptionSubItem NOACCOUNTVIEW = new NumericExceptionSubItem("005", "NoAccountView");
    public static final NumericExceptionSubItem NOSUPPORTMORESETT = new NumericExceptionSubItem("006", "NOSUPPORTMORESETT");
    public static final NumericExceptionSubItem NOTNULL = new NumericExceptionSubItem("007", "NOTNULL");
    public static final NumericExceptionSubItem SIMPLENOCOST = new NumericExceptionSubItem("008", "SIMPLENOCOST");
    public static final NumericExceptionSubItem SIMPLENOFINANCIAL = new NumericExceptionSubItem("009", "SIMPLENOFINANCIAL");
    public static final NumericExceptionSubItem COSTNOSIMPLE = new NumericExceptionSubItem("010", "COSTNOSIMPLE");
    public static final NumericExceptionSubItem FINANCIALNOSIMPLE = new NumericExceptionSubItem("011", "FINANCIALNOSIMPLE");
    public static final NumericExceptionSubItem PAYMENTEXIST = new NumericExceptionSubItem("012", "PAYMENTEXIST");
    public static final NumericExceptionSubItem SETTELMENTCOSTSPLITEXIST = new NumericExceptionSubItem("014", "SETTELMENTCOSTSPLITEXIST");
    public static final NumericExceptionSubItem COSTCANTSETTLEMENT = new NumericExceptionSubItem("015", "COSTCANTSETTLEMENT");
    public static final NumericExceptionSubItem COMPLEXCANTSETTLEMENT = new NumericExceptionSubItem("016", "COMPLEXCANTSETTLEMENT");
    public static final NumericExceptionSubItem SIMPLECANTSETTLEMENT = new NumericExceptionSubItem("017", "SIMPLECANTSETTLEMENT");
    public static final NumericExceptionSubItem SETTLEMENTCANTCOST = new NumericExceptionSubItem("013", "SETTLEMENTCANTCOST");
    public static final NumericExceptionSubItem SETTLEMENTCANTCOMPLEXT = new NumericExceptionSubItem("018", "SETTLEMENTCANTCOMPLEXT");
    public static final NumericExceptionSubItem NOTOPENSIMPLE = new NumericExceptionSubItem("020", "NOTOPENSIMPLE");
    public static final NumericExceptionSubItem ENABLEFINANCEANDADJUST = new NumericExceptionSubItem("019", "ENABLEFINANCEANDADJUST");
    public static final NumericExceptionSubItem EXISTWORKLOADBILL = new NumericExceptionSubItem("021", "EXISTWORKLOADBILL");
    public static final NumericExceptionSubItem EXISTCOMPLETEPRJAMTBILL = new NumericExceptionSubItem("022", "EXISTCOMPLETEPRJAMTBILL");
    public static final NumericExceptionSubItem SEPARATE = new NumericExceptionSubItem("023", "SEPARATE");
    public static final NumericExceptionSubItem CANNOTSPLITSUBMIT = new NumericExceptionSubItem("024", "CANNOTSPLITSUBMIT");
    public static final NumericExceptionSubItem CANNOTUSECOMPLEX = new NumericExceptionSubItem("025", "CANNOTUSECOMPLEX");
    public static final NumericExceptionSubItem INVOICEACTIVE = new NumericExceptionSubItem("026", "INVOICEACTIVE");
    public static final NumericExceptionSubItem INVOICEDEACTIVE = new NumericExceptionSubItem("027", "INVOICEDEACTIVE");
    public static final NumericExceptionSubItem MORNTHANONE = new NumericExceptionSubItem("028", "MORNTHANONE");
    public static final NumericExceptionSubItem EXISTFINANCIALORG = new NumericExceptionSubItem("029", "EXISTFINANCIALORG");
    public static final NumericExceptionSubItem AIMCOSTADJUSTDELETE = new NumericExceptionSubItem("030", "AIMCOSTADJUSTDELETE");
    public static final NumericExceptionSubItem NOTCANCELCANELISCONTROLPAYMENT = new NumericExceptionSubItem("031", "NOTCANCELCANELISCONTROLPAYMENT");
    public static final NumericExceptionSubItem ENABLESIMPLE = new NumericExceptionSubItem("032", "ENABLESIMPLE");
    public static final NumericExceptionSubItem SIMPLEINVOICEENABLED = new NumericExceptionSubItem("033", "SIMPLEINVOICEENABLED");
    public static final NumericExceptionSubItem BASEONTASKMUSTDISABLEANOTHER = new NumericExceptionSubItem("034", "BASEONTASKMUSTDISABLEANOTHER");
    public static final NumericExceptionSubItem BASEONCONTRACTMUSTDISABLEANOTHER = new NumericExceptionSubItem("035", "BASEONCONTRACTMUSTDISABLEANOTHER");
    public static final NumericExceptionSubItem MUSTSPLIT = new NumericExceptionSubItem("036", "MUSTSPLIT");
    public static final NumericExceptionSubItem SPLITALLENABLED = new NumericExceptionSubItem("037", "SPLITALLENABLED");
    public static final NumericExceptionSubItem CANSPLITSUBMIT = new NumericExceptionSubItem("038", "CANSPLITSUBMIT");
    public static final NumericExceptionSubItem MULTIPROJECT = new NumericExceptionSubItem("039", "MULTIPROJECT");
    public static final NumericExceptionSubItem CROSSPROJECTSPLIT = new NumericExceptionSubItem("040", "CROSSPROJECTSPLIT");
    public static final NumericExceptionSubItem FDC311_UPDATE_EXCEPTION = new NumericExceptionSubItem("041", "FDC311_UPDATE_EXCEPTION");
    public static final NumericExceptionSubItem FDC325_UPDATE_EXCEPTION = new NumericExceptionSubItem("042", "FDC325_UPDATE_EXCEPTION");

    /**
     * construct function
     * @param NumericExceptionSubItem info
     * @param Throwable cause
     * @param Object[] params
     */
    public FDCException(NumericExceptionSubItem info, Throwable cause, Object[] params)
    {
        super(info, cause, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Object[] params
     */
    public FDCException(NumericExceptionSubItem info, Object[] params)
    {
        this(info, null, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Throwable cause
     */
    public FDCException(NumericExceptionSubItem info, Throwable cause)
    {
        this(info, cause, null);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info
     */
    public FDCException(NumericExceptionSubItem info)
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