/**
 * output package name
 */
package com.kingdee.eas.fdc.material;

import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.eas.common.EASBizException;

/**
 * output class name
 */
public class MaterialException extends EASBizException
{
    private static final String MAINCODE = "99";

    public static final NumericExceptionSubItem REF_NOT_DELETE = new NumericExceptionSubItem("100", "REF_NOT_DELETE");
    public static final NumericExceptionSubItem REF_NOT_EDIT = new NumericExceptionSubItem("101", "REF_NOT_EDIT");
    public static final NumericExceptionSubItem REF_NOT_UNAUDIT = new NumericExceptionSubItem("102", "REF_NOT_UNAUDIT");
    public static final NumericExceptionSubItem REF_NOT_CANCELPAY = new NumericExceptionSubItem("103", "REF_NOT_CANCELPAY");
    public static final NumericExceptionSubItem EXIST_RECET_VERSION = new NumericExceptionSubItem("104", "EXIST_RECET_VERSION");

    /**
     * construct function
     * @param NumericExceptionSubItem info
     * @param Throwable cause
     * @param Object[] params
     */
    public MaterialException(NumericExceptionSubItem info, Throwable cause, Object[] params)
    {
        super(info, cause, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Object[] params
     */
    public MaterialException(NumericExceptionSubItem info, Object[] params)
    {
        this(info, null, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Throwable cause
     */
    public MaterialException(NumericExceptionSubItem info, Throwable cause)
    {
        this(info, cause, null);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info
     */
    public MaterialException(NumericExceptionSubItem info)
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