/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.scheme;

import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.eas.common.EASBizException;

/**
 * output class name
 */
public class SchemeException extends EASBizException
{
    private static final String MAINCODE = "01";

    public static final NumericExceptionSubItem START_IS_NULL = new NumericExceptionSubItem("000", "START_IS_NULL");
    public static final NumericExceptionSubItem EARLIER_CURRENT = new NumericExceptionSubItem("001", "EARLIER_CURRENT");
    public static final NumericExceptionSubItem NOT_CURRENT = new NumericExceptionSubItem("002", "NOT_CURRENT");
    public static final NumericExceptionSubItem EXIST_CIRRENT = new NumericExceptionSubItem("003", "EXIST_CIRRENT");
    public static final NumericExceptionSubItem NOT_START = new NumericExceptionSubItem("004", "NOT_START");
    public static final NumericExceptionSubItem EXIST_UNAUDIT = new NumericExceptionSubItem("005", "EXIST_UNAUDIT");
    public static final NumericExceptionSubItem HAS_CLINIT = new NumericExceptionSubItem("006", "HAS_CLINIT");
    public static final NumericExceptionSubItem HAS_NTINIT = new NumericExceptionSubItem("007", "HAS_NTINIT");
    public static final NumericExceptionSubItem HAS_CLPERIOD = new NumericExceptionSubItem("008", "HAS_CLPERIOD");
    public static final NumericExceptionSubItem HAS_NTPERIOD = new NumericExceptionSubItem("009", "HAS_NTPERIOD");
    public static final NumericExceptionSubItem HAS_COSTITEM = new NumericExceptionSubItem("010", "HAS_COSTITEM");
    public static final NumericExceptionSubItem EXIST_AUDIT = new NumericExceptionSubItem("011", "EXIST_AUDIT");
    public static final NumericExceptionSubItem EXIST_CIRRENT2 = new NumericExceptionSubItem("012", "EXIST_CIRRENT2");
    public static final NumericExceptionSubItem NEXT_NULL = new NumericExceptionSubItem("013", "NEXT_NULL");
    public static final NumericExceptionSubItem EARLIER_START = new NumericExceptionSubItem("014", "EARLIER_START");
    public static final NumericExceptionSubItem EXISTS_TURNDEPRE = new NumericExceptionSubItem("015", "EXISTS_TURNDEPRE");
    public static final NumericExceptionSubItem COST_IS_CLOSE = new NumericExceptionSubItem("016", "COST_IS_CLOSE");
    public static final NumericExceptionSubItem EQU_IS_CLOSE = new NumericExceptionSubItem("017", "EQU_IS_CLOSE");

    /**
     * construct function
     * @param NumericExceptionSubItem info
     * @param Throwable cause
     * @param Object[] params
     */
    public SchemeException(NumericExceptionSubItem info, Throwable cause, Object[] params)
    {
        super(info, cause, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Object[] params
     */
    public SchemeException(NumericExceptionSubItem info, Object[] params)
    {
        this(info, null, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Throwable cause
     */
    public SchemeException(NumericExceptionSubItem info, Throwable cause)
    {
        this(info, cause, null);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info
     */
    public SchemeException(NumericExceptionSubItem info)
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