/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata;

import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.eas.common.EASBizException;

/**
 * output class name
 */
public class CostSplitException extends EASBizException
{
    private static final String MAINCODE = "02";

    public static final NumericExceptionSubItem COSTSPLIT_DUP = new NumericExceptionSubItem("000", "COSTSPLIT_DUP");
    public static final NumericExceptionSubItem CONNOTAUDIT = new NumericExceptionSubItem("001", "CONNOTAUDIT");
    public static final NumericExceptionSubItem PARTSPLIT = new NumericExceptionSubItem("002", "PARTSPLIT");
    public static final NumericExceptionSubItem NOBEACCOUNT = new NumericExceptionSubItem("003", "NOBEACCOUNT");
    public static final NumericExceptionSubItem ISAUDITTED = new NumericExceptionSubItem("004", "ISAUDITTED");

    /**
     * construct function
     * @param NumericExceptionSubItem info
     * @param Throwable cause
     * @param Object[] params
     */
    public CostSplitException(NumericExceptionSubItem info, Throwable cause, Object[] params)
    {
        super(info, cause, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Object[] params
     */
    public CostSplitException(NumericExceptionSubItem info, Object[] params)
    {
        this(info, null, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Throwable cause
     */
    public CostSplitException(NumericExceptionSubItem info, Throwable cause)
    {
        this(info, cause, null);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info
     */
    public CostSplitException(NumericExceptionSubItem info)
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